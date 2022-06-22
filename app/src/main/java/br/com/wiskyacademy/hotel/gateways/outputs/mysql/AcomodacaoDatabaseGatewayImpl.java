package br.com.wiskyacademy.hotel.gateways.outputs.mysql;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.specifications.AcomodacaoSpecification.toSpec;
import static java.util.stream.Collectors.toList;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.FiltroAcomodacao;
import br.com.wiskyacademy.hotel.gateways.AcomodacaoDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.AcomodacaoRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcomodacaoDatabaseGatewayImpl implements AcomodacaoDatabaseGateway {

  private final AcomodacaoRepository acomodacaoRepository;

  @Override
  public Acomodacao save(final Acomodacao acomodacao) {
    return acomodacaoRepository.save(new AcomodacaoEntity(acomodacao)).toDomain();
  }

  @Override
  public Optional<Acomodacao> findById(final Integer id) {
    return acomodacaoRepository.findById(id).map(AcomodacaoEntity::toDomain);
  }

  @Override
  public Page<Acomodacao> search(final FiltroAcomodacao filtro, final Pageable pageable) {
    final Page<AcomodacaoEntity> page = acomodacaoRepository.findAll(toSpec(filtro), pageable);
    return new PageImpl<>(
        page.getContent().stream().map(AcomodacaoEntity::toDomain).collect(toList()),
        page.getPageable(),
        page.getTotalElements());
  }
}