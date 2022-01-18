package br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "acomodacao")
public class AcomodacaoEntity {

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

  public AcomodacaoEntity(final Acomodacao acomodacao) {
    this.id = acomodacao.getId();
    this.nome = acomodacao.getNome();
    this.descricao = acomodacao.getDescricao();
    this.capacidade = acomodacao.getCapacidade();
    this.preco = acomodacao.getPreco();
  }

  public Acomodacao toDomain() {
    return Acomodacao.builder()
        .id(this.id)
        .nome(this.nome)
        .descricao(this.descricao)
        .capacidade(this.capacidade)
        .preco(this.preco)
        .build();
  }
}