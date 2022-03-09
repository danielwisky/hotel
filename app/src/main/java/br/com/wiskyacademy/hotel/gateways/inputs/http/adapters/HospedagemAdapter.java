package br.com.wiskyacademy.hotel.gateways.inputs.http.adapters;

import static java.time.LocalDateTime.now;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.AcomodacaoDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.AcompanhanteDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.HospedagemRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HospedagemAdapter {

  private final AcompanhanteDatabaseGateway acompanhanteDatabaseGateway;
  private final AcomodacaoDatabaseGateway acomodacaoDatabaseGateway;
  private final HospedeDatabaseGateway hospedeDatabaseGateway;

  public Hospedagem to(final HospedagemRequest request) {
    return Hospedagem.builder()
        .acomodacao(buscaAcomodacao(request.getAcomodacao()))
        .hospede(buscaHospede(request.getHospede()))
        .acompanhantes(buscaAcompanhantes(request.getAcompanhantes(), request.getHospede()))
        .dataEntrada(request.getDataEntrada())
        .dataSaida(request.getDataSaida())
        .dataAtualizacao(now())
        .build();
  }

  private List<Acompanhante> buscaAcompanhantes(
      final List<Integer> acompanhantesId, final Integer hospedeId) {
    return acompanhanteDatabaseGateway
        .findByHospedeIdAndAcompanhantesId(hospedeId, acompanhantesId);
  }

  private Hospede buscaHospede(final Integer hospedeId) {
    return hospedeDatabaseGateway.findById(hospedeId)
        .orElseThrow(ResourceNotFoundException::new);
  }

  private Acomodacao buscaAcomodacao(final Integer acomodacaoId) {
    return acomodacaoDatabaseGateway.findById(acomodacaoId)
        .orElseThrow(ResourceNotFoundException::new);
  }

}
