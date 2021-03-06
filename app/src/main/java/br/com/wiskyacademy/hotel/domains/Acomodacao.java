package br.com.wiskyacademy.hotel.domains;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Acomodacao {

  private Integer id;
  private String nome;
  private String descricao;
  private Integer capacidade;
  private Float preco;
  private Boolean ativo;
  private LocalDateTime dataAtualizacao;
}