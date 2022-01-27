package br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities;

import static java.util.Optional.ofNullable;

import br.com.wiskyacademy.hotel.domains.Hospede;
import java.time.LocalDate;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "hospede")
public class HospedeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false, length = 100)
  private String nome;
  @Column(nullable = false, length = 20)
  private String documento;
  @Column(nullable = false, name = "dt_nascimento")
  private LocalDate dataNascimento;
  @OneToOne(mappedBy = "hospede", cascade = CascadeType.ALL, orphanRemoval = true)
  private EnderecoEntity endereco;
  @Column(nullable = false, length = 100, unique = true)
  private String email;
  @Column(length = 20)
  private String telefone;
  @Column(nullable = false, length = 20)
  private String celular;

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
        .build();
  }
}