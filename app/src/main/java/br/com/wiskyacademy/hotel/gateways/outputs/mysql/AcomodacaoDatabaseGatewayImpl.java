package br.com.wiskyacademy.hotel.gateways.outputs.mysql;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.gateways.AcomodacaoDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.AcomodacaoRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcomodacaoDatabaseGatewayImpl implements AcomodacaoDatabaseGateway {

  private final AcomodacaoRepository repository;

  @Override
  public Acomodacao save(final Acomodacao acomodacao) {
    return repository.save(new AcomodacaoEntity(acomodacao)).toDomain();
  }

  @Override
  public Optional<Acomodacao> findById(final Integer id) {
    return repository.findById(id).map(AcomodacaoEntity::toDomain);
  }
}