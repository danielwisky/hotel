package br.com.wiskyacademy.hotel.gateways.outputs.mysql;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.specifications.AcompanhanteSpecification.toSpec;
import static java.util.stream.Collectors.toList;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.FiltroAcompanhante;
import br.com.wiskyacademy.hotel.gateways.AcompanhanteDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcompanhanteEntity;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.AcompanhanteRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcompanhanteDatabaseGatewayImpl implements AcompanhanteDatabaseGateway {

  private final AcompanhanteRepository acompanhanteRepository;

  @Override
  public Acompanhante save(final Acompanhante acompanhante) {
    return acompanhanteRepository.save(new AcompanhanteEntity(acompanhante)).toDomain();
  }

  @Override
  public Optional<Acompanhante> findByHospedeIdAndAcompanhanteId(
      final Integer hospedeId, final Integer acompanhanteId) {
    return acompanhanteRepository.findByHospedeIdAndId(hospedeId, acompanhanteId)
        .map(AcompanhanteEntity::toDomain);
  }

  @Override
  public List<Acompanhante> findByHospedeIdAndAcompanhantesId(
      final Integer hospedeId, final List<Integer> acompanhantesId) {
    return acompanhanteRepository.findByHospedeIdAndIdIn(hospedeId, acompanhantesId)
        .stream()
        .map(AcompanhanteEntity::toDomain).collect(toList());
  }

  @Override
  public Page<Acompanhante> search(final FiltroAcompanhante filtro, final Pageable pageable) {
    final Page<AcompanhanteEntity> page = acompanhanteRepository.findAll(toSpec(filtro), pageable);
    return new PageImpl<>(
        page.getContent().stream().map(AcompanhanteEntity::toDomain).collect(toList()),
        page.getPageable(),
        page.getTotalElements());
  }
}