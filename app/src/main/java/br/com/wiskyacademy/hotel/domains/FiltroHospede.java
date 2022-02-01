package br.com.wiskyacademy.hotel.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltroHospede {

  private String nome;
  private String documento;
}
