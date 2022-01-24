package br.com.wiskyacademy.hotel.gateways.outputs.mysql;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.CAPACIDADE;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.DESCRICAO;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.NOME;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.PRECO;
import static br.com.wiskyacademy.hotel.utils.CriteriaUtils.addGreaterThanOrEqualToIfNotNull;
import static br.com.wiskyacademy.hotel.utils.CriteriaUtils.addLessThanOrEqualToIfNotNull;
import static br.com.wiskyacademy.hotel.utils.CriteriaUtils.addLikeConditionIfNotBlank;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.FiltroAcomodacao;
import br.com.wiskyacademy.hotel.gateways.AcomodacaoDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.AcomodacaoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

  @Override
  public Page<Acomodacao> search(final FiltroAcomodacao filtro, final Pageable pageable) {
    final Page<AcomodacaoEntity> page = repository.findAll(toSpec(filtro), pageable);
    return new PageImpl<>(
        page.getContent().stream().map(AcomodacaoEntity::toDomain).collect(Collectors.toList()),
        page.getPageable(),
        page.getTotalElements());
  }

  private Specification<AcomodacaoEntity> toSpec(final FiltroAcomodacao filtro) {
    return ((root, query, builder) -> {
      final List<Predicate> predicates = new ArrayList<>();
      addLikeConditionIfNotBlank(builder, predicates, filtro.getNome(), root.get(NOME));
      addLikeConditionIfNotBlank(builder, predicates, filtro.getDescricao(), root.get(DESCRICAO));
      addGreaterThanOrEqualToIfNotNull(builder, predicates, filtro.getCapacidadeMaiorQue(), root.get(CAPACIDADE));
      addLessThanOrEqualToIfNotNull(builder, predicates, filtro.getCapacidadeMenorQue(), root.get(CAPACIDADE));
      addGreaterThanOrEqualToIfNotNull(builder, predicates, filtro.getPrecoMaiorQue(), root.get(PRECO));
      addLessThanOrEqualToIfNotNull(builder, predicates, filtro.getPrecoMenorQue(), root.get(PRECO));
      return builder.and(predicates.toArray(new Predicate[0]));
    });
  }
}