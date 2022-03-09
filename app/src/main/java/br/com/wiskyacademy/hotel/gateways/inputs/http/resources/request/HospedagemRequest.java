package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HospedagemRequest implements Serializable {

  private static final long serialVersionUID = 2201638271745786671L;

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
