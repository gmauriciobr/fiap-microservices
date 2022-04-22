package br.com.fiap.validation;

import br.com.fiap.repository.FotoRepository;
import java.util.Set;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FotosValidator implements ConstraintValidator<FotosConstraint, Set<String>> {

  private final FotoRepository fotoRepository;

  @Override
  public boolean isValid(Set<String> value, ConstraintValidatorContext context) {
    return fotoRepository.existsByTokenIn(value);
  }
}
