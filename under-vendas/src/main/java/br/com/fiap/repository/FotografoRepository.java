package br.com.fiap.repository;

import br.com.fiap.model.Fotografo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotografoRepository extends CrudRepository<Fotografo, Long> {
}
