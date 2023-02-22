package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

import static java.time.LocalDateTime.now;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Data;

@Data
public class AcomodacaoRequest implements Serializable {

  private static final long serialVersionUID = 1660105230360907424L;

  @NotBlank
  private String nome;
  private String descricao;
  @NotNull
  private Integer capacidade;
  @NotNull
  private Float preco;
  private boolean ativo;

  public Acomodacao toDomain() {
    return Acomodacao.builder()
        .nome(this.nome)
        .descricao(this.descricao)
        .capacidade(this.capacidade)
        .preco(this.preco)
        .ativo(this.ativo)
        .dataAtualizacao(now())
        .build();
  }
}
