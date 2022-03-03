package br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.specifications;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedeEntity_.DOCUMENTO;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedeEntity_.ENDERECO;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedeEntity_.NOME;
import static br.com.wiskyacademy.hotel.utils.CriteriaUtils.addLikeConditionIfNotBlank;

import br.com.wiskyacademy.hotel.domains.FiltroHospede;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HospedeSpecification {

  public static Specification<HospedeEntity> toSpec(final FiltroHospede filtro) {
    return ((root, query, builder) -> {
      final List<Predicate> predicates = new ArrayList<>();
      addLikeConditionIfNotBlank(builder, predicates, filtro.getNome(), root.get(NOME));
      addLikeConditionIfNotBlank(builder, predicates, filtro.getDocumento(), root.get(DOCUMENTO));

      root.fetch(ENDERECO);

      return builder.and(predicates.toArray(new Predicate[0]));
    });
  }
}
