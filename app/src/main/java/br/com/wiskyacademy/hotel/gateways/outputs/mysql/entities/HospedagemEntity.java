package br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.StatusHospedagem;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "hospedagem")
public class HospedagemEntity implements Serializable {

  private static final long serialVersionUID = -2789582521438839808L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToOne
  @JoinColumn(name = "acomodacao_id", nullable = false)
  private AcomodacaoEntity acomodacao;
  @ManyToOne
  @JoinColumn(name = "hospede_id", nullable = false)
  private HospedeEntity hospede;
  @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
  @JoinTable(name = "acompanhante_hospedagem", joinColumns = @JoinColumn(name = "hospedagem_id"), inverseJoinColumns = @JoinColumn(name = "acompanhante_id"))
  private List<AcompanhanteEntity> acompanhantes;
  @Column(nullable = false)
  private Float valor;
  @Column(name = "valor_pago")
  private Float valorPago;
  @Column(name = "dt_entrada", nullable = false)
  private LocalDate dataEntrada;
  @Column(name = "dt_saida", nullable = false)
  private LocalDate dataSaida;
  @Column(name = "dt_check_in")
  private LocalDateTime dataCheckIn;
  @Column(name = "dt_check_out")
  private LocalDateTime dataCheckOut;
  private String status;
  @Column(name = "dt_atualizacao")
  private LocalDateTime dataAtualizacao;

  public HospedagemEntity(final Hospedagem hospedagem) {
    this.id = hospedagem.getId();
    this.acomodacao = ofNullable(hospedagem.getAcomodacao())
        .map(AcomodacaoEntity::new)
        .orElse(null);
    this.hospede = ofNullable(hospedagem.getHospede())
        .map(HospedeEntity::new)
        .orElse(null);
    this.acompanhantes = emptyIfNull(hospedagem.getAcompanhantes())
        .stream()
        .map(AcompanhanteEntity::new)
        .collect(toList());
    this.valor = hospedagem.getValor();
    this.valorPago = hospedagem.getValorPago();
    this.dataEntrada = hospedagem.getDataEntrada();
    this.dataSaida = hospedagem.getDataSaida();
    this.dataCheckIn = hospedagem.getDataCheckIn();
    this.dataCheckOut = hospedagem.getDataCheckOut();
    this.status = ofNullable(hospedagem.getStatus())
        .map(Enum::name)
        .orElse(null);
    this.dataAtualizacao = hospedagem.getDataAtualizacao();
  }

  public Hospedagem toDomain() {
    return Hospedagem.builder()
        .id(this.id)
        .acomodacao(ofNullable(this.acomodacao)
            .map(AcomodacaoEntity::toDomain)
            .orElse(null))
        .hospede(ofNullable(this.hospede)
            .map(HospedeEntity::toDomain)
            .orElse(null))
        .acompanhantes(emptyIfNull(this.acompanhantes)
            .stream()
            .map(AcompanhanteEntity::toDomain)
            .collect(toList()))
        .valor(this.valor)
        .valorPago(this.valorPago)
        .dataEntrada(this.dataEntrada)
        .dataSaida(this.dataSaida)
        .dataCheckIn(this.dataCheckIn)
        .dataCheckOut(this.dataCheckOut)
        .status(ofNullable(this.status)
            .map(StatusHospedagem::valueOf)
            .orElse(null))
        .dataAtualizacao(this.dataAtualizacao)
        .build();
  }
}