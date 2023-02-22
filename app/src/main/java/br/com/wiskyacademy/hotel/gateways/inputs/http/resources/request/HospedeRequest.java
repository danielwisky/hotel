package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

import static java.time.LocalDateTime.now;

import br.com.wiskyacademy.hotel.domains.Hospede;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;
import lombok.Data;

@Data
public class HospedeRequest implements Serializable {

  private static final long serialVersionUID = -180650138397225773L;

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
  private boolean ativo;

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
        .ativo(this.ativo)
        .dataAtualizacao(now())
        .build();
  }
}
