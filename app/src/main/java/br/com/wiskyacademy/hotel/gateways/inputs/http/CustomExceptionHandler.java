package br.com.wiskyacademy.hotel.gateways.inputs.http;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.ErroResponse;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public HttpEntity<ErroResponse> handlerResourceNotFoundException(
      final ResourceNotFoundException ex) {
    return new ResponseEntity<>(createMessage(ex), createHeaders(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public HttpEntity<ErroResponse> handlerValidationException(
      final MethodArgumentNotValidException ex) {

    final BindingResult bindingResult = ex.getBindingResult();
    final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    final ErroResponse message = this.processFieldErrors(fieldErrors);

    return new ResponseEntity<>(message, createHeaders(), BAD_REQUEST);
  }

  private HttpHeaders createHeaders() {
    final HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add(CONTENT_TYPE, APPLICATION_JSON_VALUE);
    return responseHeaders;
  }

  private ErroResponse createMessage(final Throwable ex) {
    ErroResponse message = null;
    if (isNotBlank(ex.getMessage())) {
      message = new ErroResponse(List.of(ex.getMessage()));
    }
    return message;
  }

  private ErroResponse processFieldErrors(final List<FieldError> fieldErrors) {
    final List<String> errors = fieldErrors.stream()
        .map(a -> String.format("%s: %s", a.getField(), a.getDefaultMessage()))
        .collect(toList());
    return new ErroResponse(errors);
  }
}
