package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response;

import static java.util.Optional.ofNullable;
import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

import br.com.wiskyacademy.hotel.domains.Hospedagem;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class HospedagemResponse implements Serializable {

  private static final long serialVersionUID = -6806865903452998061L;

  private Integer id;
  private AcomodacaoResponse acomodacao;
  private HospedeResponse hospede;
  private List<AcompanhanteResponse> acompanhantes;
  private Float valor;
  private Float valorPago;
  private LocalDate dataEntrada;
  private LocalDate dataSaida;
  private LocalDateTime dataCheckIn;
  private LocalDateTime dataCheckOut;
  private String status;
  private LocalDateTime dataAtualizacao;

  public HospedagemResponse(final Hospedagem hospedagem) {
    this.id = hospedagem.getId();
    this.acomodacao = ofNullable(hospedagem.getAcomodacao())
        .map(AcomodacaoResponse::new)
        .orElse(null);
    this.hospede = ofNullable(hospedagem.getHospede())
        .map(HospedeResponse::new)
        .orElse(null);
    this.acompanhantes = emptyIfNull(hospedagem.getAcompanhantes())
        .stream().map(AcompanhanteResponse::new)
        .collect(Collectors.toList());
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
}
