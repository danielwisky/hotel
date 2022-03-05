package br.com.wiskyacademy.hotel.gateways.outputs.mysql;

import br.com.wiskyacademy.hotel.domains.Endereco;
import br.com.wiskyacademy.hotel.gateways.EnderecoDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.EnderecoEntity;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.EnderecoRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnderecoDatabaseGatewayImpl implements EnderecoDatabaseGateway {

  private final EnderecoRepository enderecoRepository;

  @Override
  public Endereco save(final Endereco endereco) {
    return enderecoRepository.save(new EnderecoEntity(endereco)).toDomain();
  }

  @Override
  public Optional<Endereco> findById(final Integer id) {
    return enderecoRepository.findById(id).map(EnderecoEntity::toDomain);
  }
}