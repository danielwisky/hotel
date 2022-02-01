package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

import br.com.wiskyacademy.hotel.domains.Endereco;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EnderecoRequest {

  @NotBlank
  private String cep;
  @NotBlank
  private String numero;
  @NotBlank
  private String bairro;
  @NotBlank
  private String cidade;
  @NotBlank
  private String estado;
  @NotBlank
  private String logradouro;
  private String complemento;

  public Endereco toDomain() {
    return Endereco.builder()
        .cep(this.cep)
        .numero(this.numero)
        .bairro(this.bairro)
        .cidade(this.cidade)
        .estado(this.estado)
        .logradouro(this.logradouro)
        .complemento(this.complemento)
        .build();
  }
}
