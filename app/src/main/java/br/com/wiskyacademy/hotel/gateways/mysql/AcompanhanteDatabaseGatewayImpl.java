package br.com.wiskyacademy.hotel.gateways.mysql;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.gateways.AcompanhanteDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.mysql.entities.AcompanhanteEntity;
import br.com.wiskyacademy.hotel.gateways.mysql.repositories.AcompanhanteRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AcompanhanteDatabaseGatewayImpl implements AcompanhanteDatabaseGateway {

  private final AcompanhanteRepository repository;

  @Override
  public Acompanhante save(final Acompanhante acompanhante) {
    return repository.save(new AcompanhanteEntity(acompanhante)).toDomain();
  }

  @Override
  public Optional<Acompanhante> findById(final Integer id) {
    return repository.findById(id).map(AcompanhanteEntity::toDomain);
  }
}