package br.com.fiap.model;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Entity
@NoArgsConstructor
public class Fotografo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nome;

  private String email;

  @OneToMany(mappedBy = "fotografo", cascade = CascadeType.REMOVE)
  private List<Album> albums;

  @CreatedDate
  private LocalDateTime dataCriacao;

  @LastModifiedDate
  private LocalDateTime dataAlteracao;

  public Fotografo(Long id) {
    this.id = id;
  }

}
