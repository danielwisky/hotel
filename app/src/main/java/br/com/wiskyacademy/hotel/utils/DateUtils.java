package br.com.wiskyacademy.hotel.utils;

import static java.time.LocalDateTime.now;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

  public static boolean anteriorDataMinimaAtual(final LocalDate data) {
    return now().isAfter(LocalDateTime.of(data, LocalTime.MIN));
  }

  public static boolean posteriorDataMaximaAtual(final LocalDate data) {
    return now().isBefore(LocalDateTime.of(data, LocalTime.MAX));
  }
}
