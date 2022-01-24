package br.com.wiskyacademy.hotel.gateways.inputs.http.resources;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErroResponse {

  private List<String> erros;
}
