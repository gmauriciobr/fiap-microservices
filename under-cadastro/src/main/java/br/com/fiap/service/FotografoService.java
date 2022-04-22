package br.com.fiap.service;

import br.com.fiap.dto.FotografoDTO;
import br.com.fiap.dto.FotografoUpdateDTO;
import br.com.fiap.model.Fotografo;
import br.com.fiap.repository.FotografoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class FotografoService {

  private final FotografoRepository fotografoRepository;

  public Fotografo buscaPorId(Long id) {
    return fotografoRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fotografo não encontrado"));
  }

  public Fotografo cadastra(FotografoDTO dto) {
    var fotografo = FotografoDTO.parse(dto);
    return fotografoRepository.save(fotografo);
  }

  public Fotografo atualiza(Long id, FotografoUpdateDTO dto) {
    var fotografo = buscaPorId(id);
    BeanUtils.copyProperties(dto, fotografo);
    return fotografoRepository.save(fotografo);
  }

  public void deleta(Long id) {
    fotografoRepository.delete(buscaPorId(id));
  }
}
