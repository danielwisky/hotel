package br.com.wiskyacademy.hotel.utils;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static java.time.LocalTime.MAX;
import static java.time.LocalTime.MIN;

import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

  public static boolean isAfterOfCurrentDate(final LocalDate data) {
    return now().isAfter(of(data, MIN));
  }

  public static boolean isBeforeOfCurrentDate(final LocalDate data) {
    return now().isBefore(of(data, MAX));
  }

  public static boolean isBetweenCurrentPeriod(final LocalDate startDate, final LocalDate endDate) {
    return isAfterOfCurrentDate(startDate) && isBeforeOfCurrentDate(endDate);
  }
}