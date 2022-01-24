package br.com.wiskyacademy.hotel.utils;

import static java.util.Objects.nonNull;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CriteriaUtils {

  public static void addGreaterThanOrEqualToIfNotNull(
      final CriteriaBuilder builder,
      final List<Predicate> predicates,
      final Float value,
      final Path<Float> path) {

    if (nonNull(value)) {
      final Predicate like = builder.greaterThanOrEqualTo(path, value);
      predicates.add(like);
    }
  }

  public static void addGreaterThanOrEqualToIfNotNull(
      final CriteriaBuilder builder,
      final List<Predicate> predicates,
      final Integer value,
      final Path<Integer> path) {

    if (nonNull(value)) {
      final Predicate like = builder.greaterThanOrEqualTo(path, value);
      predicates.add(like);
    }
  }

  public static void addLessThanOrEqualToIfNotNull(
      final CriteriaBuilder builder,
      final List<Predicate> predicates,
      final Float value,
      final Path<Float> path) {

    if (nonNull(value)) {
      final Predicate like = builder.lessThanOrEqualTo(path, value);
      predicates.add(like);
    }
  }

  public static void addLessThanOrEqualToIfNotNull(
      final CriteriaBuilder builder,
      final List<Predicate> predicates,
      final Integer value,
      final Path<Integer> path) {

    if (nonNull(value)) {
      final Predicate like = builder.lessThanOrEqualTo(path, value);
      predicates.add(like);
    }
  }

  public static void addLikeConditionIfNotBlank(
      final CriteriaBuilder builder,
      final List<Predicate> predicates,
      final String value,
      final Path<String> field) {

    if (StringUtils.isNotBlank(value)) {
      final Predicate like = builder.like(field, String.format("%%%s%%", value));
      predicates.add(like);
    }
  }

}