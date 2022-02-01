package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

import br.com.wiskyacademy.hotel.domains.FiltroHospede;
import lombok.Data;

@Data
public class FiltroHospedeRequest {

  private String nome;
  private String documento;

  public FiltroHospede toDomain() {
    return FiltroHospede.builder()
        .nome(this.nome)
        .documento(this.documento)
        .build();
  }
}
