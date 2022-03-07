package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcomodacaoResponse {

  private Integer id;
  private String nome;
  private String descricao;
  private Integer capacidade;
  private Float preco;
  private Boolean ativo;
  private LocalDateTime dataAtualizacao;

  public AcomodacaoResponse(final Acomodacao acomodacao) {
    this.id = acomodacao.getId();
    this.nome = acomodacao.getNome();
    this.descricao = acomodacao.getDescricao();
    this.capacidade = acomodacao.getCapacidade();
    this.preco = acomodacao.getPreco();
    this.ativo = acomodacao.getAtivo();
    this.dataAtualizacao = acomodacao.getDataAtualizacao();
  }
}
