package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response;

import br.com.wiskyacademy.hotel.domains.Endereco;
import lombok.Data;

@Data
public class EnderecoResponse {

  private String cep;
  private String numero;
  private String bairro;
  private String cidade;
  private String estado;
  private String logradouro;
  private String complemento;

  public EnderecoResponse(final Endereco endereco) {
    this.cep = endereco.getCep();
    this.numero = endereco.getNumero();
    this.bairro = endereco.getBairro();
    this.cidade = endereco.getCidade();
    this.estado = endereco.getEstado();
    this.logradouro = endereco.getLogradouro();
    this.complemento = endereco.getComplemento();
  }
}
