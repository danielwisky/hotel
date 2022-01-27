package br.com.wiskyacademy.hotel.gateways.inputs.http.resources;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AcomodacaoRequest {

  @NotBlank
  private String nome;
  private String descricao;
  @NotNull
  private Integer capacidade;
  @NotNull
  private Float preco;

  public Acomodacao toDomain() {
    return Acomodacao.builder()
        .nome(this.nome)
        .descricao(this.descricao)
        .capacidade(this.capacidade)
        .preco(this.preco)
        .build();
  }
}
