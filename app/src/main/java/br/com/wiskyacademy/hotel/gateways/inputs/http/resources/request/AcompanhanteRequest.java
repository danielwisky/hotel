package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

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

  public Acompanhante toDomain() {
    return Acompanhante.builder()
        .nome(this.nome)
        .documento(this.documento)
        .dataNascimento(this.dataNascimento)
        .build();
  }
}
