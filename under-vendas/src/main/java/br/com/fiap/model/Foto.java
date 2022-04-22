package br.com.fiap.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Foto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Album album;

  @Column(nullable = false, updatable = false, columnDefinition = "char")
  private String token;

  private String filename;

  @Column(columnDefinition = "longblob")
  private byte[] fotoOriginal;

  @Column(columnDefinition = "longblob")
  private byte[] fotoBaixaQualidade;

  @CreatedDate
  private LocalDateTime dataCriacao;

  @LastModifiedDate
  private LocalDateTime dataAlteracao;

}
