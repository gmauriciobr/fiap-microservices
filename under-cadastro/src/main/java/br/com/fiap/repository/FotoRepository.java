package br.com.fiap.repository;

import br.com.fiap.model.Foto;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface FotoRepository extends CrudRepository<Foto, Long> {

  Optional<Foto> findByAlbumIdAndId(Long albumId, Long id);
}
