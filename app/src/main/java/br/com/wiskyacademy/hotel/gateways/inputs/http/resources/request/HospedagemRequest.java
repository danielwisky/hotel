package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HospedagemRequest {

  @NotNull
  private Integer acomodacao;
  @NotNull
  private Integer hospede;
  private List<Integer> acompanhantes;
  @NotNull
  private LocalDate dataEntrada;
  @NotNull
  private LocalDate dataSaida;
}
