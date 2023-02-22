package br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.specifications;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.CAPACIDADE;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.DESCRICAO;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.NOME;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.PRECO;
import static br.com.wiskyacademy.hotel.utils.CriteriaUtils.addGreaterThanOrEqualToIfNotNull;
import static br.com.wiskyacademy.hotel.utils.CriteriaUtils.addLessThanOrEqualToIfNotNull;
import static br.com.wiskyacademy.hotel.utils.CriteriaUtils.addLikeConditionIfNotBlank;

import br.com.wiskyacademy.hotel.domains.FiltroAcomodacao;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AcomodacaoSpecification {

  public static Specification<AcomodacaoEntity> toSpec(final FiltroAcomodacao filtro) {
    return ((root, query, builder) -> {
      final List<Predicate> predicates = new ArrayList<>();
      addLikeConditionIfNotBlank(builder, predicates, filtro.getNome(), root.get(NOME));
      addLikeConditionIfNotBlank(builder, predicates, filtro.getDescricao(), root.get(DESCRICAO));
      addGreaterThanOrEqualToIfNotNull(
          builder, predicates, filtro.getCapacidadeMaiorQue(), root.get(CAPACIDADE));
      addLessThanOrEqualToIfNotNull(
          builder, predicates, filtro.getCapacidadeMenorQue(), root.get(CAPACIDADE));
      addGreaterThanOrEqualToIfNotNull(
          builder, predicates, filtro.getPrecoMaiorQue(), root.get(PRECO));
      addLessThanOrEqualToIfNotNull(
          builder, predicates, filtro.getPrecoMenorQue(), root.get(PRECO));
      return builder.and(predicates.toArray(new Predicate[0]));
    });
  }
}
