package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

import static java.time.LocalDateTime.now;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AcompanhanteRequest {

  @NotBlank
  private String nome;
  @NotBlank
  private String documento;
  private LocalDate dataNascimento;
  private boolean ativo;

  public Acompanhante toDomain() {
    return Acompanhante.builder()
        .nome(this.nome)
        .documento(this.documento)
        .dataNascimento(this.dataNascimento)
        .ativo(this.ativo)
        .dataAtualizacao(now())
        .build();
  }
}
