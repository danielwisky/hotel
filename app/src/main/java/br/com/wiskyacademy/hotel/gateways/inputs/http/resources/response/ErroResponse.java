package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErroResponse implements Serializable {

  private static final long serialVersionUID = 2880537706208093002L;

  private List<String> erros;
}
