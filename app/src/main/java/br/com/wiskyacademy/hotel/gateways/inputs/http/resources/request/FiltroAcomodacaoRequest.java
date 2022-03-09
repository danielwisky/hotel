package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

import br.com.wiskyacademy.hotel.domains.FiltroAcomodacao;
import java.io.Serializable;
import lombok.Data;

@Data
public class FiltroAcomodacaoRequest implements Serializable {

  private static final long serialVersionUID = 1883485362704522311L;

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
