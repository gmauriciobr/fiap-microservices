package br.com.fiap.service;

import static br.com.fiap.config.rabbitmq.RabbitMQConfig.PROCESSA_IMAGEM_DIRECT_QUEUE;

import br.com.fiap.dto.ProcessamentoFotoDTO;
import br.com.fiap.model.Foto;
import br.com.fiap.repository.FotoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class FotoService {

  private final FotoRepository fotoRepository;

  private final AlbumService albumService;

  private final RabbitTemplate rabbitTemplate;

  public Foto buscaFoto(Long albumId, Long id) {
    return fotoRepository.findByAlbumIdAndId(albumId, id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto não encontrada"));
  }

  public void uploadFoto(List<MultipartFile> files, Long albumId) {
    var album = albumService.buscaPorId(albumId);
    files.forEach(file -> {
      try {
        var token = UUID.randomUUID().toString();
        var foto = Foto.builder()
          .token(token)
          .album(album)
          .filename(file.getOriginalFilename())
          .fotoOriginal(file.getBytes())
          .build();
        fotoRepository.save(foto);
        enviaProcessamentoImagem(foto);
      } catch (IOException e) {
        log.error("Erro ao salvar foto Album [{}] - [{}]", albumId, file.getOriginalFilename());
      }
    });
  }

  private void enviaProcessamentoImagem(Foto foto) {
    var dto = ProcessamentoFotoDTO.builder()
      .id(foto.getId())
      .filename(foto.getFilename())
      .file(foto.getFotoOriginal())
      .qualidade(foto.getAlbum().getQualidade())
      .build();
    rabbitTemplate.convertAndSend(PROCESSA_IMAGEM_DIRECT_QUEUE, dto);
  }

  public void salvarFotoBaixaQualidade(Message message) throws Exception {
    try {
      ProcessamentoFotoDTO dto = new ObjectMapper().readValue(message.getBody(), ProcessamentoFotoDTO.class);
      fotoRepository.findById(dto.getId()).ifPresent(foto -> {
        foto.setFotoBaixaQualidade(dto.getFile());
        fotoRepository.save(foto);
      });
    } catch (Exception e) {
      log.error("Erro ao salvar foto de baixa qualidade", e);
      throw e;
    }
  }

  public void deleta(Long albumId, Long id) {
    var foto = buscaFoto(albumId, id);
    fotoRepository.delete(foto);
  }
}
