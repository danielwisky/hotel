package br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.specifications;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedagemEntity_.ACOMODACAO;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedagemEntity_.ACOMPANHANTES;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedagemEntity_.DATA_CHECK_IN;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedagemEntity_.DATA_CHECK_OUT;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedagemEntity_.DATA_ENTRADA;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedagemEntity_.DATA_SAIDA;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedagemEntity_.HOSPEDE;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedagemEntity_.STATUS;
import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedeEntity_.ENDERECO;
import static br.com.wiskyacademy.hotel.utils.CriteriaUtils.addEqualConditionIfNotNull;
import static br.com.wiskyacademy.hotel.utils.CriteriaUtils.addGreaterThanOrEqualToIfNotNull;
import static br.com.wiskyacademy.hotel.utils.CriteriaUtils.addLessThanOrEqualToIfNotNull;

import br.com.wiskyacademy.hotel.domains.FiltroHospedagem;
import br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.HospedagemEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HospedagemSpecification {

  public static Specification<HospedagemEntity> toSpec(final FiltroHospedagem filtro) {
    return ((root, query, builder) -> {
      final List<Predicate> predicates = new ArrayList<>();
      addEqualConditionIfNotNull(builder, predicates, filtro.getAcomodacao(), root.get(ACOMODACAO));
      addEqualConditionIfNotNull(builder, predicates, filtro.getHospede(), root.get(HOSPEDE));
      addGreaterThanOrEqualToIfNotNull(
          builder, predicates, filtro.getDataEntradaMaiorQue(), root.get(DATA_ENTRADA));
      addLessThanOrEqualToIfNotNull(
          builder, predicates, filtro.getDataEntradaMenorQue(), root.get(DATA_ENTRADA));
      addGreaterThanOrEqualToIfNotNull(
          builder, predicates, filtro.getDataSaidaMaiorQue(), root.get(DATA_SAIDA));
      addLessThanOrEqualToIfNotNull(
          builder, predicates, filtro.getDataSaidaMenorQue(), root.get(DATA_SAIDA));
      addGreaterThanOrEqualToIfNotNull(
          builder, predicates, filtro.getDataCheckInMaiorQue(), root.get(DATA_CHECK_IN));
      addLessThanOrEqualToIfNotNull(
          builder, predicates, filtro.getDataCheckInMenorQue(), root.get(DATA_CHECK_IN));
      addGreaterThanOrEqualToIfNotNull(
          builder, predicates, filtro.getDataCheckOutMaiorQue(), root.get(DATA_CHECK_OUT));
      addLessThanOrEqualToIfNotNull(
          builder, predicates, filtro.getDataCheckOutMenorQue(), root.get(DATA_CHECK_OUT));
      addEqualConditionIfNotNull(
          builder, predicates, filtro.getStatus(), root.get(STATUS));

      root.fetch(HOSPEDE).fetch(ENDERECO);
      root.fetch(ACOMPANHANTES);
      root.fetch(ACOMODACAO);

      return builder.and(predicates.toArray(new Predicate[0]));
    });
  }
}
