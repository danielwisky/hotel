package br.com.wiskyacademy.hotel.domains;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hospedagem {

  private Integer id;
  private Acomodacao acomodacao;
  private Hospede hospede;
  private List<Acompanhante> acompanhantes;
  private Float valor;
  private Float valorPago;
  private LocalDate dataEntrada;
  private LocalDate dataSaida;
  private LocalDateTime dataCheckIn;
  private LocalDateTime dataCheckOut;
  private StatusHospedagem status;
  private LocalDateTime dataAtualizacao;
}