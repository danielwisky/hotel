package br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities;

import br.com.wiskyacademy.hotel.domains.Endereco;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "endereco")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EnderecoEntity implements Serializable {

  private static final long serialVersionUID = 1179589141758507364L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false, length = 10)
  private String cep;
  @Column(nullable = false, length = 10)
  private String numero;
  @Column(nullable = false, length = 100)
  private String bairro;
  @Column(nullable = false, length = 100)
  private String cidade;
  @Column(nullable = false, length = 40)
  private String estado;
  @Column(nullable = false, length = 140)
  private String logradouro;
  @Column(length = 100)
  private String complemento;
  @OneToOne(orphanRemoval = true)
  @JoinColumn(name = "hospede_id")
  @ToString.Exclude
  private HospedeEntity hospede;

  public EnderecoEntity(final Endereco endereco) {
    this.id = endereco.getId();
    this.cep = endereco.getCep();
    this.numero = endereco.getNumero();
    this.bairro = endereco.getBairro();
    this.cidade = endereco.getCidade();
    this.estado = endereco.getEstado();
    this.logradouro = endereco.getLogradouro();
    this.complemento = endereco.getComplemento();
  }

  public Endereco toDomain() {
    return Endereco.builder()
        .id(this.id)
        .cep(this.cep)
        .numero(this.numero)
        .bairro(this.bairro)
        .cidade(this.cidade)
        .estado(this.estado)
        .logradouro(this.logradouro)
        .complemento(this.complemento)
        .build();
  }
}