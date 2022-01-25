package br.com.wiskyacademy.hotel.gateways.inputs.http;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.ID;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.AcomodacaoDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.AcomodacaoRequest;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.AcomodacaoResponse;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.FiltroAcomodacaoRequest;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.PageResponse;
import br.com.wiskyacademy.hotel.usecases.UpdateAcomodacao;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

  private final UpdateAcomodacao updateAcomodacao;

  @PostMapping
  @ResponseStatus(OK)
  @ApiOperation(value = "Criar uma acomodação")
  public ResponseEntity<AcomodacaoResponse> criar(
      @RequestBody @Valid final AcomodacaoRequest acomodacaoRequest) {

    return ResponseEntity.ok(
        new AcomodacaoResponse(acomodacaoDatabaseGateway.save(acomodacaoRequest.toDomain())));
  }

  @PutMapping("/{id}")
  @ResponseStatus(NO_CONTENT)
  @ApiOperation(value = "Editar uma acomodação")
  public ResponseEntity editar(
      @PathVariable final Integer id,
      @RequestBody @Valid final AcomodacaoRequest acomodacaoRequest) {

    updateAcomodacao.execute(id, acomodacaoRequest.toDomain());
    return ResponseEntity.noContent().build();
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
  public ResponseEntity<PageResponse<AcomodacaoResponse>> pesquisar(
      final FiltroAcomodacaoRequest filtro,
      @RequestParam(defaultValue = "0") final Integer pagina,
      @RequestParam(defaultValue = "20") final Integer tamanho) {
    final Page<Acomodacao> resultado =
        acomodacaoDatabaseGateway.search(filtro.toDomain(), of(pagina, tamanho, ASC, ID));

    return ResponseEntity.ok(new PageResponse<>(
        resultado.getContent().stream().map(AcomodacaoResponse::new).collect(toList()),
        resultado.getTotalElements(),
        resultado.getTotalPages(),
        pagina,
        tamanho));
  }
}
