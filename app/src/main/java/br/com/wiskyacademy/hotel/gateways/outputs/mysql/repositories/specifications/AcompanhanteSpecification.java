package br.com.wiskyacademy.hotel.gateways.outputs.mysql.repositories.specifications;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcompanhanteEntity_.DOCUMENTO;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcompanhanteEntity_.HOSPEDE;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcompanhanteEntity_.NOME;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedeEntity_.ENDERECO;
import static br.com.wiskyacademy.hotel.utils.CriteriaUtils.addEqualConditionIfNotNull;
import static br.com.wiskyacademy.hotel.utils.CriteriaUtils.addLikeConditionIfNotBlank;

import br.com.wiskyacademy.hotel.domains.FiltroAcompanhante;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcompanhanteEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AcompanhanteSpecification {

  public static Specification<AcompanhanteEntity> toSpec(final FiltroAcompanhante filtro) {
    return ((root, query, builder) -> {
      final List<Predicate> predicates = new ArrayList<>();
      addEqualConditionIfNotNull(builder, predicates, filtro.getHospedeId(), root.get(HOSPEDE));
      addLikeConditionIfNotBlank(builder, predicates, filtro.getNome(), root.get(NOME));
      addLikeConditionIfNotBlank(builder, predicates, filtro.getDocumento(), root.get(DOCUMENTO));

      root
          .fetch(HOSPEDE)
          .fetch(ENDERECO);

      return builder.and(predicates.toArray(new Predicate[0]));
    });
  }
}
