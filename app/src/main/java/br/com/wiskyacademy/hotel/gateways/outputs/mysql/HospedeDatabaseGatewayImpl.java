package br.com.wiskyacademy.hotel.gateways.outputs.mysql;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.specifications.HospedeSpecification.toSpec;
import static java.util.stream.Collectors.toList;

import br.com.wiskyacademy.hotel.domains.FiltroHospede;
import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedeEntity;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.HospedeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HospedeDatabaseGatewayImpl implements HospedeDatabaseGateway {

  private final HospedeRepository hospedeRepository;

  @Override
  public Hospede save(final Hospede hospede) {
    return hospedeRepository.save(new HospedeEntity(hospede)).toDomain();
  }

  @Override
  public Optional<Hospede> findById(final Integer id) {
    return hospedeRepository.findById(id).map(HospedeEntity::toDomain);
  }

  @Override
  public Page<Hospede> search(final FiltroHospede filtro, final Pageable pageable) {
    final Page<HospedeEntity> page = hospedeRepository.findAll(toSpec(filtro), pageable);
    return new PageImpl<>(
        page.getContent().stream().map(HospedeEntity::toDomain).collect(toList()),
        page.getPageable(),
        page.getTotalElements());
  }
}