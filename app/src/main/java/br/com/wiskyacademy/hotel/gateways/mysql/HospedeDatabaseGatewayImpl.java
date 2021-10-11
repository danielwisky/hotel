package br.com.wiskyacademy.hotel.gateways.mysql;

import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.mysql.entities.HospedeEntity;
import br.com.wiskyacademy.hotel.gateways.mysql.repositories.HospedeRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HospedeDatabaseGatewayImpl implements HospedeDatabaseGateway {

  private final HospedeRepository repository;

  @Override
  public Hospede save(final Hospede hospede) {
    return repository.save(new HospedeEntity(hospede)).toDomain();
  }

  @Override
  public Optional<Hospede> findById(final Integer id) {
    return repository.findById(id).map(HospedeEntity::toDomain);
  }
}