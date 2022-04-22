package br.com.fiap.validation;

import br.com.fiap.repository.FotografoRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FotografoValidator implements ConstraintValidator<FotografoConstraint, Long> {

  private final FotografoRepository fotografoRepository;

  @Override
  public boolean isValid(Long value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }
    return fotografoRepository.existsById(value);
  }
}
