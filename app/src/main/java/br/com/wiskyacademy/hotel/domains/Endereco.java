package br.com.wiskyacademy.hotel.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

  private Integer id;
  private String cep;
  private String numero;
  private String bairro;
  private String cidade;
  private String estado;
  private String logradouro;
  private String complemento;
}