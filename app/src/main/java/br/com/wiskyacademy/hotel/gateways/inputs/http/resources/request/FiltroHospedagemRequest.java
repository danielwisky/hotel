package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

import br.com.wiskyacademy.hotel.domains.FiltroHospedagem;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class FiltroHospedagemRequest implements Serializable {

  private static final long serialVersionUID = 774099081029244111L;

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
  private String status;

  public FiltroHospedagem toDomain() {
    return FiltroHospedagem.builder()
        .acomodacao(this.acomodacao)
        .hospede(this.hospede)
        .dataEntradaMaiorQue(this.dataEntradaMaiorQue)
        .dataEntradaMenorQue(this.dataEntradaMenorQue)
        .dataSaidaMaiorQue(this.dataSaidaMaiorQue)
        .dataSaidaMenorQue(this.dataSaidaMenorQue)
        .dataCheckInMaiorQue(this.dataCheckInMaiorQue)
        .dataCheckInMenorQue(this.dataCheckInMenorQue)
        .dataCheckOutMaiorQue(this.dataCheckOutMaiorQue)
        .dataCheckOutMenorQue(this.dataCheckOutMenorQue)
        .status(this.status)
        .build();
  }
}
