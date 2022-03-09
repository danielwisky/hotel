package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

import br.com.wiskyacademy.hotel.domains.FiltroHospede;
import java.io.Serializable;
import lombok.Data;

@Data
public class FiltroHospedeRequest implements Serializable {

  private static final long serialVersionUID = 5351573393225905062L;
  private String nome;
  private String documento;

  public FiltroHospede toDomain() {
    return FiltroHospede.builder()
        .nome(this.nome)
        .documento(this.documento)
        .build();
  }
}
