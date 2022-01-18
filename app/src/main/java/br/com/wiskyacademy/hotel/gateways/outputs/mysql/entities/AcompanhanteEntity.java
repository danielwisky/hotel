package br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

import static java.util.Optional.ofNullable;

@Data
@NoArgsConstructor
@Entity(name = "acompanhante")
public class AcompanhanteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false, length = 100)
  private String nome;
  @Column(nullable = false, length = 20)
  private String documento;
  @Column(nullable = false, name = "dt_nascimento")
  private LocalDate dataNascimento;
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "hospede_id", nullable = false)
  private HospedeEntity hospede;

  public AcompanhanteEntity(final Acompanhante acompanhante) {
    this.id = acompanhante.getId();
    this.nome = acompanhante.getNome();
    this.documento = acompanhante.getDocumento();
    this.dataNascimento = acompanhante.getDataNascimento();
    this.hospede = ofNullable(acompanhante.getHospede())
        .map(HospedeEntity::new)
        .orElse(null);
  }

  public Acompanhante toDomain() {
    return Acompanhante.builder()
        .id(this.id)
        .nome(this.nome)
        .documento(this.documento)
        .dataNascimento(this.dataNascimento)
        .hospede(ofNullable(this.hospede)
            .map(HospedeEntity::toDomain)
            .orElse(null))
        .build();
  }
}