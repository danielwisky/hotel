package br.com.wiskyacademy.hotel.gateways.inputs.http;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.ID;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.OK;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.AcomodacaoDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.AcomodacaoRequest;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.AcomodacaoResponse;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.FiltroAcomodacaoRequest;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/acomodacoes")
public class AcomodacaoController {

  private final AcomodacaoDatabaseGateway acomodacaoDatabaseGateway;

  @PostMapping
  @ResponseStatus(OK)
  @ApiOperation(value = "Criar uma acomodação")
  public ResponseEntity<AcomodacaoResponse> criar(
      @RequestBody @Valid final AcomodacaoRequest acomodacaoRequest) {

    return ResponseEntity.ok(
        new AcomodacaoResponse(acomodacaoDatabaseGateway.save(acomodacaoRequest.toDomain())));
  }

  @PutMapping("/{id}")
  @ResponseStatus(OK)
  @ApiOperation(value = "Editar uma acomodação")
  public ResponseEntity<AcomodacaoResponse> editar(
      @PathVariable final Integer id,
      @RequestBody @Valid final AcomodacaoRequest acomodacaoRequest) {

    return ResponseEntity.ok(
        new AcomodacaoResponse(acomodacaoDatabaseGateway.save(acomodacaoRequest.toDomain())));
  }

  @GetMapping("/{id}")
  @ResponseStatus(OK)
  @ApiOperation(value = "Buscar uma acomodação por id")
  public ResponseEntity<AcomodacaoResponse> buscar(@PathVariable final Integer id) {
    return ResponseEntity.ok(acomodacaoDatabaseGateway
        .findById(id)
        .map(AcomodacaoResponse::new)
        .orElseThrow(ResourceNotFoundException::new));
  }

  @GetMapping
  @ResponseStatus(OK)
  @ApiOperation(value = "Pesquisa acomodações cadastradas")
  public ResponseEntity<Page<AcomodacaoResponse>> pesquisar(
      final FiltroAcomodacaoRequest filtro,
      @RequestParam(defaultValue = "0") final Integer page,
      @RequestParam(defaultValue = "20") final Integer size) {
    final Page<Acomodacao> resultado =
        acomodacaoDatabaseGateway.search(filtro.toDomain(), PageRequest.of(page, size, ASC, ID));
    return ResponseEntity.ok(
        new PageImpl<>(
            resultado.getContent().stream().map(AcomodacaoResponse::new).collect(toList()),
            resultado.getPageable(),
            resultado.getTotalElements()));
  }
}
