package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request;

import br.com.wiskyacademy.hotel.domains.Endereco;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import lombok.Data;

@Data
public class EnderecoRequest implements Serializable {

  private static final long serialVersionUID = 5418465645065305274L;

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
