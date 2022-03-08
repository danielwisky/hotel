package br.com.wiskyacademy.hotel.domains.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BusinessValidationException extends RuntimeException {

  public BusinessValidationException(final String message) {
    super(message);
  }
}
