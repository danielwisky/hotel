package br.com.wiskyacademy.hotel.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltroAcomodacao {

  private String nome;
  private String descricao;
  private Integer capacidadeMaiorQue;
  private Integer capacidadeMenorQue;
  private Float precoMaiorQue;
  private Float precoMenorQue;
}
