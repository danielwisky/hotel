package br.com.wiskyacademy.hotel.utils;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.wiskyacademy.hotel.UnitTest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class CriteriaUtilsTest extends UnitTest {

  @Mock
  private CriteriaBuilder criteriaBuilder;

  @Test
  public void deveAdicionarMaiorOuIgualQuandoValorInteiroNaoForNulo() {
    final List<Predicate> predicates = new ArrayList<>();
    final Integer valor = 10;
    CriteriaUtils.addGreaterThanOrEqualToIfNotNull(criteriaBuilder, predicates, valor, null);

    assertEquals(INTEGER_ONE, predicates.size());
  }

  @Test
  public void naoDeveAdicionarMaiorOuIgualQuandoInteiroValorForNulo() {
    final List<Predicate> predicates = new ArrayList<>();
    final Integer valor = null;
    CriteriaUtils.addGreaterThanOrEqualToIfNotNull(criteriaBuilder, predicates, valor, null);

    assertEquals(INTEGER_ZERO, predicates.size());
  }

  @Test
  public void deveAdicionarMaiorOuIgualQuandoValorDecimalNaoForNulo() {
    final List<Predicate> predicates = new ArrayList<>();
    final Float valor = 10.0f;
    CriteriaUtils.addGreaterThanOrEqualToIfNotNull(criteriaBuilder, predicates, valor, null);

    assertEquals(INTEGER_ONE, predicates.size());
  }

  @Test
  public void naoDeveAdicionarMaiorOuIgualQuandoDecimalValorForNulo() {
    final List<Predicate> predicates = new ArrayList<>();
    final Float valor = null;
    CriteriaUtils.addGreaterThanOrEqualToIfNotNull(criteriaBuilder, predicates, valor, null);

    assertEquals(INTEGER_ZERO, predicates.size());
  }

  @Test
  public void deveAdicionarMenorOuIgualQuandoValorInteiroNaoForNulo() {
    final List<Predicate> predicates = new ArrayList<>();
    final Integer valor = 10;
    CriteriaUtils.addLessThanOrEqualToIfNotNull(criteriaBuilder, predicates, valor, null);

    assertEquals(INTEGER_ONE, predicates.size());
  }

  @Test
  public void naoDeveAdicionarMenorOuIgualQuandoInteiroValorForNulo() {
    final List<Predicate> predicates = new ArrayList<>();
    final Integer valor = null;
    CriteriaUtils.addLessThanOrEqualToIfNotNull(criteriaBuilder, predicates, valor, null);

    assertEquals(INTEGER_ZERO, predicates.size());
  }

  @Test
  public void deveAdicionarMenorOuIgualQuandoValorDecimalNaoForNulo() {
    final List<Predicate> predicates = new ArrayList<>();
    final Float valor = 10.0f;
    CriteriaUtils.addLessThanOrEqualToIfNotNull(criteriaBuilder, predicates, valor, null);

    assertEquals(INTEGER_ONE, predicates.size());
  }

  @Test
  public void naoDeveAdicionarMenorOuIgualQuandoDecimalValorForNulo() {
    final List<Predicate> predicates = new ArrayList<>();
    final Float valor = null;
    CriteriaUtils.addLessThanOrEqualToIfNotNull(criteriaBuilder, predicates, valor, null);

    assertEquals(INTEGER_ZERO, predicates.size());
  }

  @Test
  public void deveAdicionarContemQuandoValorNaoForNuloOuVazio() {
    final List<Predicate> predicates = new ArrayList<>();
    final String valor = "valor";
    CriteriaUtils.addLikeConditionIfNotBlank(criteriaBuilder, predicates, valor, null);

    assertEquals(INTEGER_ONE, predicates.size());
  }

  @Test
  public void naoDeveAdicionarContemQuandoValorVazio() {
    final List<Predicate> predicates = new ArrayList<>();
    final String valor = "";
    CriteriaUtils.addLikeConditionIfNotBlank(criteriaBuilder, predicates, valor, null);

    assertEquals(INTEGER_ZERO, predicates.size());
  }

  @Test
  public void naoDeveAdicionarContemQuandoValorNulo() {
    final List<Predicate> predicates = new ArrayList<>();
    final String valor = null;
    CriteriaUtils.addLikeConditionIfNotBlank(criteriaBuilder, predicates, valor, null);

    assertEquals(INTEGER_ZERO, predicates.size());
  }

  @Test
  public void deveAdicionarIgualQuandoValorNaoForNulo() {
    final List<Predicate> predicates = new ArrayList<>();
    final Integer valor = 1;
    CriteriaUtils.addEqualConditionIfNotNull(criteriaBuilder, predicates, valor, null);

    assertEquals(INTEGER_ONE, predicates.size());
  }

  @Test
  public void naoDeveAdicionarIgualQuandoValorNulo() {
    final List<Predicate> predicates = new ArrayList<>();
    final Integer valor = null;
    CriteriaUtils.addEqualConditionIfNotNull(criteriaBuilder, predicates, valor, null);

    assertEquals(INTEGER_ZERO, predicates.size());
  }
}
