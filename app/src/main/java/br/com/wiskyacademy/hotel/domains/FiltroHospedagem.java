package br.com.wiskyacademy.hotel.domains;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltroHospedagem {

  private Integer acomodacao;
  private Integer hospede;
  private LocalDate dataEntradaMaiorQue;
  private LocalDate dataEntradaMenorQue;
  private LocalDate dataSaidaMaiorQue;
  private LocalDate dataSaidaMenorQue;
  private LocalDateTime dataCheckInMaiorQue;
  private LocalDateTime dataCheckInMenorQue;
  private LocalDateTime dataCheckOutMaiorQue;
  private LocalDateTime dataCheckOutMenorQue;
  private LocalDate periodoMaiorQue;
  private LocalDate periodoMenorQue;
  private String status;
}