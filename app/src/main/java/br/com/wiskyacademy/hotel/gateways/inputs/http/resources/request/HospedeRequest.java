package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

import static java.time.LocalDateTime.now;

import br.com.wiskyacademy.hotel.domains.Hospede;
import java.time.LocalDate;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HospedeRequest {

  @NotBlank
  private String nome;
  @NotBlank
  private String documento;
  private LocalDate dataNascimento;
  @Email
  private String email;
  private String telefone;
  @NotBlank
  private String celular;
  @Valid
  @NotNull
  private EnderecoRequest endereco;

  public Hospede toDomain() {
    return Hospede.builder()
        .nome(this.nome)
        .documento(this.documento)
        .dataNascimento(this.dataNascimento)
        .email(this.email)
        .telefone(this.telefone)
        .celular(this.celular)
        .endereco(Optional.ofNullable(this.endereco)
            .map(EnderecoRequest::toDomain)
            .orElse(null))
        .dataAtualizacao(now())
        .build();
  }
}
