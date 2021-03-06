package br.com.wiskyacademy.hotel.gateways.outputs.mysql;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.specifications.HospedagemSpecification.toSpec;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.specifications.HospedagemSpecification.toSpecWithoutFetch;
import static java.util.stream.Collectors.toList;

import br.com.wiskyacademy.hotel.domains.FiltroHospedagem;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.StatusHospedagem;
import br.com.wiskyacademy.hotel.gateways.HospedagemDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedagemEntity;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.HospedagemRepository;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HospedagemDatabaseGatewayImpl implements HospedagemDatabaseGateway {

  private final HospedagemRepository hospedagemRepository;

  @Override
  public Hospedagem save(final Hospedagem hospedagem) {
    return hospedagemRepository.save(new HospedagemEntity(hospedagem)).toDomain();
  }

  @Override
  public Optional<Hospedagem> findById(final Integer id) {
    return hospedagemRepository.findById(id).map(HospedagemEntity::toDomain);
  }

  @Override
  public Page<Hospedagem> search(final FiltroHospedagem filtro, final Pageable pageable) {
    final Page<HospedagemEntity> page = hospedagemRepository.findAll(toSpec(filtro), pageable);
    return new PageImpl<>(
        page.getContent().stream().map(HospedagemEntity::toDomain).collect(toList()),
        page.getPageable(),
        page.getTotalElements());
  }

  @Override
  public boolean existsByAcomodacaoAndPeriodoDeEstadia(
      final Integer acomodacaoId, final LocalDate dataEntrada, final LocalDate dataSaida) {
    final FiltroHospedagem filtro = FiltroHospedagem.builder()
        .acomodacao(acomodacaoId)
        .periodoMenorQue(dataSaida)
        .periodoMaiorQue(dataEntrada)
        .statusNot(StatusHospedagem.CANCELADO.name())
        .build();
    return hospedagemRepository.count(toSpecWithoutFetch(filtro)) > 0;
  }
}