package br.com.wiskyacademy.hotel.domains;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hospede {

  private Integer id;
  private String nome;
  private String documento;
  private LocalDate dataNascimento;
  private Endereco endereco;
  private String email;
  private String telefone;
  private String celular;
}