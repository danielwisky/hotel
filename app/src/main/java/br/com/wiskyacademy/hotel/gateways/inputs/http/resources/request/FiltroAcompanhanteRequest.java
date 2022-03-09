package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

import br.com.wiskyacademy.hotel.domains.FiltroAcompanhante;
import java.io.Serializable;
import lombok.Data;

@Data
public class FiltroAcompanhanteRequest implements Serializable {

  private static final long serialVersionUID = 7282130099000437979L;

  private String nome;
  private String documento;

  public FiltroAcompanhante toDomain(final Integer hospedeId) {
    return FiltroAcompanhante.builder()
        .hospedeId(hospedeId)
        .nome(this.nome)
        .documento(this.documento)
        .build();
  }
}
