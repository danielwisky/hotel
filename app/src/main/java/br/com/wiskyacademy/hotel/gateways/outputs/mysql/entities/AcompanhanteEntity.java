package br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities;

import static java.util.Optional.ofNullable;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "acompanhante")
public class AcompanhanteEntity implements Serializable {

  private static final long serialVersionUID = -7845960504921789067L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(nullable = false, length = 100)
  private String nome;
  @Column(nullable = false, length = 20)
  private String documento;
  @Column(nullable = false, name = "dt_nascimento")
  private LocalDate dataNascimento;
  @ManyToOne
  @JoinColumn(name = "hospede_id", nullable = false)
  private HospedeEntity hospede;
  private Boolean ativo;
  @Column(name = "dt_atualizacao")
  private LocalDateTime dataAtualizacao;

  public AcompanhanteEntity(final Acompanhante acompanhante) {
    this.id = acompanhante.getId();
    this.nome = acompanhante.getNome();
    this.documento = acompanhante.getDocumento();
    this.dataNascimento = acompanhante.getDataNascimento();
    this.hospede = ofNullable(acompanhante.getHospede())
        .map(HospedeEntity::new)
        .orElse(null);
    this.ativo = acompanhante.getAtivo();
    this.dataAtualizacao = acompanhante.getDataAtualizacao();
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
        .ativo(this.ativo)
        .dataAtualizacao(this.dataAtualizacao)
        .build();
  }
}