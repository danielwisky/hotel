package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AcomodacaoResponse implements Serializable {

  private static final long serialVersionUID = -7444529101318354312L;

  private Integer id;
  private String nome;
  private String descricao;
  private Integer capacidade;
  private Float preco;
  private Boolean ativo;
  @JsonSerialize(using = LocalDateTimeSerializer.class)
  @JsonDeserialize(using = LocalDateTimeDeserializer.class)
  private LocalDateTime dataAtualizacao;

  public AcomodacaoResponse(final Acomodacao acomodacao) {
    this.id = acomodacao.getId();
    this.nome = acomodacao.getNome();
    this.descricao = acomodacao.getDescricao();
    this.capacidade = acomodacao.getCapacidade();
    this.preco = acomodacao.getPreco();
    this.ativo = acomodacao.getAtivo();
    this.dataAtualizacao = acomodacao.getDataAtualizacao();
  }
}
