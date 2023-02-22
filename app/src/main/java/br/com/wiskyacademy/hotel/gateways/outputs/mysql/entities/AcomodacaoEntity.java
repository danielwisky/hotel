package br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "acomodacao")
public class AcomodacaoEntity implements Serializable {

  private static final long serialVersionUID = -7937000691165184582L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false, length = 100)
  private String nome;
  @Lob
  private String descricao;
  @Column(nullable = false, length = 2)
  private Integer capacidade;
  @Column(nullable = false)
  private Float preco;
  private Boolean ativo;
  @Column(name = "dt_atualizacao")
  private LocalDateTime dataAtualizacao;

  public AcomodacaoEntity(final Acomodacao acomodacao) {
    this.id = acomodacao.getId();
    this.nome = acomodacao.getNome();
    this.descricao = acomodacao.getDescricao();
    this.capacidade = acomodacao.getCapacidade();
    this.preco = acomodacao.getPreco();
    this.ativo = acomodacao.getAtivo();
    this.dataAtualizacao = acomodacao.getDataAtualizacao();
  }

  public Acomodacao toDomain() {
    return Acomodacao.builder()
        .id(this.id)
        .nome(this.nome)
        .descricao(this.descricao)
        .capacidade(this.capacidade)
        .preco(this.preco)
        .ativo(this.ativo)
        .dataAtualizacao(this.dataAtualizacao)
        .build();
  }
}