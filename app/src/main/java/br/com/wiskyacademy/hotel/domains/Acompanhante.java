package br.com.wiskyacademy.hotel.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Acompanhante {

  private Integer id;
  private String nome;
  private String documento;
  private LocalDate dataNascimento;
  private Hospede hospede;
}