package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response;

import br.com.wiskyacademy.hotel.domains.Hospede;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class HospedeResponse implements Serializable {

  private static final long serialVersionUID = 2568168741970016110L;

  private Integer id;
  private String nome;
  private String documento;
  private LocalDate dataNascimento;
  private String email;
  private String telefone;
  private String celular;
  private EnderecoResponse endereco;
  private Boolean ativo;
  private LocalDateTime dataAtualizacao;

  public HospedeResponse(final Hospede hospede) {
    this.id = hospede.getId();
    this.nome = hospede.getNome();
    this.documento = hospede.getDocumento();
    this.dataNascimento = hospede.getDataNascimento();
    this.email = hospede.getEmail();
    this.telefone = hospede.getTelefone();
    this.celular = hospede.getCelular();
    this.endereco = Optional.ofNullable(hospede.getEndereco())
        .map(EnderecoResponse::new)
        .orElse(null);
    this.ativo = hospede.getAtivo();
    this.dataAtualizacao = hospede.getDataAtualizacao();
  }
}
