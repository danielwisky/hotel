package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AcompanhanteResponse {

  private Integer id;
  private String nome;
  private String documento;
  private LocalDate dataNascimento;
  private Boolean ativo;
  private LocalDateTime dataAtualizacao;

  public AcompanhanteResponse(final Acompanhante acompanhante) {
    this.id = acompanhante.getId();
    this.nome = acompanhante.getNome();
    this.documento = acompanhante.getDocumento();
    this.dataNascimento = acompanhante.getDataNascimento();
    this.ativo = acompanhante.getAtivo();
    this.dataAtualizacao = acompanhante.getDataAtualizacao();
  }
}
