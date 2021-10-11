package br.com.wiskyacademy.hotel.gateways.mysql;

import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.six2six.fixturefactory.Fixture;
import br.com.wiskyacademy.hotel.UnitTest;
import br.com.wiskyacademy.hotel.domains.Endereco;
import br.com.wiskyacademy.hotel.gateways.mysql.entities.EnderecoEntity;
import br.com.wiskyacademy.hotel.gateways.mysql.repositories.EnderecoRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class EnderecoDatabaseGatewayImplTest extends UnitTest {

  @InjectMocks
  private EnderecoDatabaseGatewayImpl enderecoDatabaseGateway;

  @Mock
  private EnderecoRepository enderecoRepository;

  @Test
  void deveSalvar() {
    final Endereco endereco = Fixture.from(Endereco.class).gimme(VALIDO.name());
    final EnderecoEntity enderecoEntity = new EnderecoEntity(endereco);
    when(enderecoRepository.save(enderecoEntity)).thenReturn(enderecoEntity);

    final Endereco retorno = enderecoDatabaseGateway.save(endereco);

    verify(enderecoRepository).save(enderecoEntity);
    assertNotNull(retorno);
  }

  @Test
  void aoBuscarPorIdDeveRetornarEndereco() {

    final Endereco endereco = Fixture.from(Endereco.class).gimme(VALIDO.name());
    when(enderecoRepository.findById(endereco.getId()))
        .thenReturn(Optional.of(new EnderecoEntity(endereco)));

    final Optional<Endereco> retorno =
        enderecoDatabaseGateway.findById(endereco.getId());

    assertFalse(retorno.isEmpty());
  }

  @Test
  void aoBuscarPorIdDeveRetornarVazioQuandoNaoEncontrado() {

    final Endereco endereco = Fixture.from(Endereco.class).gimme(VALIDO.name());
    when(enderecoRepository.findById(endereco.getId()))
        .thenReturn(Optional.empty());

    final Optional<Endereco> retorno =
        enderecoDatabaseGateway.findById(endereco.getId());

    assertTrue(retorno.isEmpty());
  }
}