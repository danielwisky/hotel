package br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities;

import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;

import br.com.wiskyacademy.hotel.domains.Hospede;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "hospede")
public class HospedeEntity implements Serializable {

  private static final long serialVersionUID = 7501541979913356718L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false, length = 100)
  private String nome;
  @Column(nullable = false, length = 20)
  private String documento;
  @Column(nullable = false, name = "dt_nascimento")
  private LocalDate dataNascimento;
  @OneToOne(cascade = CascadeType.ALL, mappedBy = "hospede", orphanRemoval = true)
  private EnderecoEntity endereco;
  @Column(nullable = false, length = 100, unique = true)
  private String email;
  @Column(length = 20)
  private String telefone;
  @Column(nullable = false, length = 20)
  private String celular;
  private Boolean ativo;
  @Column(name = "dt_atualizacao")
  private LocalDateTime dataAtualizacao;

  public HospedeEntity(final Hospede hospede) {
    this.id = hospede.getId();
    this.nome = hospede.getNome();
    this.documento = hospede.getDocumento();
    this.dataNascimento = hospede.getDataNascimento();
    this.endereco = ofNullable(hospede.getEndereco())
        .map(EnderecoEntity::new)
        .orElse(null);
    this.email = hospede.getEmail();
    this.telefone = hospede.getTelefone();
    this.celular = hospede.getCelular();
    this.ativo = hospede.getAtivo();
    this.dataAtualizacao = hospede.getDataAtualizacao();

    if (nonNull(this.endereco)) {
      this.endereco.setHospede(this);
    }
  }

  public Hospede toDomain() {
    return Hospede.builder()
        .id(this.id)
        .nome(this.nome)
        .documento(this.documento)
        .dataNascimento(this.dataNascimento)
        .endereco(ofNullable(this.endereco)
            .map(EnderecoEntity::toDomain)
            .orElse(null))
        .email(this.email)
        .telefone(this.telefone)
        .celular(this.celular)
        .ativo(this.ativo)
        .dataAtualizacao(this.dataAtualizacao)
        .build();
  }
}