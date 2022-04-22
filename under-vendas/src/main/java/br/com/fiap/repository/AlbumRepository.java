package br.com.fiap.repository;

import br.com.fiap.model.Album;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {

  Optional<Album> findByToken(String token);

  boolean existsByToken(String token);

}
