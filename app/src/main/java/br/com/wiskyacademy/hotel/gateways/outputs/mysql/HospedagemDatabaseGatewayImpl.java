package br.com.wiskyacademy.hotel.gateways.outputs.mysql;

import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.gateways.HospedagemDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedagemEntity;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.HospedagemRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
}