package br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageResponse<T> {

  private List<T> elementos;
  private int pagina;
  private int tamanho;
  private int totalPaginas;
  private long totalElementos;

  public PageResponse(
      final List<T> elementos,
      final long totalElementos,
      final int totalPaginas,
      final Integer pagina,
      final Integer tamanho) {

    this.elementos = elementos;
    this.totalPaginas = totalPaginas;
    this.totalElementos = totalElementos;
    this.pagina = pagina;
    this.tamanho = tamanho;
  }
}
