package br.com.wiskyacademy.hotel.gateways.inputs.http;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.ID;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.OK;

import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.HospedagemDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.FiltroHospedagemRequest;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response.HospedagemResponse;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response.PageResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/hospedagens")
public class HospedagemController {

  private final HospedagemDatabaseGateway hospedagemDatabaseGateway;

  @GetMapping("/{id}")
  @ResponseStatus(OK)
  @ApiOperation(value = "Buscar um hospedagens por id")
  public ResponseEntity<HospedagemResponse> buscar(@PathVariable final Integer id) {
    return ResponseEntity.ok(hospedagemDatabaseGateway
        .findById(id)
        .map(HospedagemResponse::new)
        .orElseThrow(ResourceNotFoundException::new));
  }

  @GetMapping
  @ResponseStatus(OK)
  @ApiOperation(value = "Pesquisa hospedagens cadastrados")
  public ResponseEntity<PageResponse<HospedagemResponse>> pesquisar(
      final FiltroHospedagemRequest filtro,
      @RequestParam(defaultValue = "0") final Integer pagina,
      @RequestParam(defaultValue = "20") final Integer tamanho) {
    final Page<Hospedagem> resultado =
        hospedagemDatabaseGateway.search(filtro.toDomain(), of(pagina, tamanho, DESC, ID));

    return ResponseEntity.ok(new PageResponse<>(
        resultado.getContent().stream().map(HospedagemResponse::new).collect(toList()),
        resultado.getTotalElements(),
        resultado.getTotalPages(),
        pagina,
        tamanho));
  }
}
