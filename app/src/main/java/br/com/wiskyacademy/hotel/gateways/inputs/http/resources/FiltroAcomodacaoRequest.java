package br.com.wiskyacademy.hotel.gateways.inputs.http.resources;

import br.com.wiskyacademy.hotel.domains.FiltroAcomodacao;
import lombok.Data;

@Data
public class FiltroAcomodacaoRequest {

  private String nome;
  private String descricao;
  private Integer capacidadeMaiorQue;
  private Integer capacidadeMenorQue;
  private Float precoMaiorQue;
  private Float precoMenorQue;

  public FiltroAcomodacao toDomain() {
    return FiltroAcomodacao.builder()
        .nome(this.nome)
        .descricao(this.descricao)
        .capacidadeMaiorQue(this.capacidadeMaiorQue)
        .capacidadeMenorQue(this.capacidadeMenorQue)
        .precoMaiorQue(this.precoMaiorQue)
        .precoMenorQue(this.precoMenorQue)
        .build();
  }
}
