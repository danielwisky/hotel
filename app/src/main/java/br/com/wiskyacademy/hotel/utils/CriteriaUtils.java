package br.com.wiskyacademy.hotel.utils;

import static java.util.Objects.nonNull;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

  public static void addGreaterThanOrEqualToIfNotNull(
      final CriteriaBuilder builder,
      final List<Predicate> predicates,
      final LocalDateTime value,
      final Path<LocalDateTime> path) {

    if (nonNull(value)) {
      final Predicate like = builder.greaterThanOrEqualTo(path, value);
      predicates.add(like);
    }
  }

  public static void addGreaterThanOrEqualToIfNotNull(
      final CriteriaBuilder builder,
      final List<Predicate> predicates,
      final LocalDate value,
      final Path<LocalDate> path) {

    if (nonNull(value)) {
      final Predicate like = builder.greaterThanOrEqualTo(path, value);
      predicates.add(like);
    }
  }

  public static void addLessThanOrEqualToIfNotNull(
      final CriteriaBuilder builder,
      final List<Predicate> predicates,
      final LocalDateTime value,
      final Path<LocalDateTime> path) {

    if (nonNull(value)) {
      final Predicate like = builder.lessThanOrEqualTo(path, value);
      predicates.add(like);
    }
  }

  public static void addLessThanOrEqualToIfNotNull(
      final CriteriaBuilder builder,
      final List<Predicate> predicates,
      final LocalDate value,
      final Path<LocalDate> path) {

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

  public static void addEqualConditionIfNotNull(
      final CriteriaBuilder builder,
      final List<Predicate> predicates,
      final Object value,
      final Path<Object> field) {

    if (nonNull(value)) {
      final Predicate like = builder.equal(field, value);
      predicates.add(like);
    }
  }

  public static void addNotEqualConditionIfNotNull(
      final CriteriaBuilder builder,
      final List<Predicate> predicates,
      final Object value,
      final Path<Object> field) {

    if (nonNull(value)) {
      final Predicate like = builder.notEqual(field, value);
      predicates.add(like);
    }
  }
}
