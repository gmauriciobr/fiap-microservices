package br.com.fiap.validation;

import br.com.fiap.repository.AlbumRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlbumValidator implements ConstraintValidator<AlbumConstraint, String> {

  private final AlbumRepository albumRepository;

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return albumRepository.existsByToken(value);
  }
}
