package br.com.wiskyacademy.hotel.gateways.mysql.entities;

import static java.util.Optional.ofNullable;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private LocalDateTime dataNascimento;
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