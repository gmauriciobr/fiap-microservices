package br.com.fiap.repository;

import br.com.fiap.model.Foto;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoRepository extends CrudRepository<Foto, Long> {

  Optional<Foto> findByToken(String token);

  boolean existsByTokenIn(Set<String> token);

  List<Foto> findByAlbumTokenAndTokenIn(String token, Set<String> tokes);

}
