package br.com.wiskyacademy.hotel.gateways.inputs.http;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.ID;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.OK;

import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.HospedeDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.FiltroHospedeRequest;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.HospedeRequest;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response.HospedeResponse;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response.PageResponse;
import br.com.wiskyacademy.hotel.usecases.UpdateHospede;
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
@RequestMapping("/api/v1/hospedes")
public class HospedeController {

  private final HospedeDatabaseGateway hospedeDatabaseGateway;

  private final UpdateHospede updateHospede;

  @PostMapping
  @ResponseStatus(OK)
  @ApiOperation(value = "Criar um hospede")
  public ResponseEntity<HospedeResponse> criar(
      @RequestBody @Valid final HospedeRequest hospedeRequest) {

    return ResponseEntity.ok(
        new HospedeResponse(hospedeDatabaseGateway.save(hospedeRequest.toDomain())));
  }

  @PutMapping("/{id}")
  @ResponseStatus(OK)
  @ApiOperation(value = "Editar um hospede")
  public ResponseEntity<HospedeResponse> editar(
      @PathVariable final Integer id,
      @RequestBody @Valid final HospedeRequest hospedeRequest) {

    return ResponseEntity.ok(
        new HospedeResponse(updateHospede.execute(id, hospedeRequest.toDomain())));
  }

  @GetMapping("/{id}")
  @ResponseStatus(OK)
  @ApiOperation(value = "Buscar um hospede por id")
  public ResponseEntity<HospedeResponse> buscar(@PathVariable final Integer id) {
    return ResponseEntity.ok(hospedeDatabaseGateway
        .findById(id)
        .map(HospedeResponse::new)
        .orElseThrow(ResourceNotFoundException::new));
  }

  @GetMapping
  @ResponseStatus(OK)
  @ApiOperation(value = "Pesquisa hospedes cadastrados")
  public ResponseEntity<PageResponse<HospedeResponse>> pesquisar(
      final FiltroHospedeRequest filtro,
      @RequestParam(defaultValue = "0") final Integer pagina,
      @RequestParam(defaultValue = "20") final Integer tamanho) {
    final Page<Hospede> resultado =
        hospedeDatabaseGateway.search(filtro.toDomain(), of(pagina, tamanho, ASC, ID));

    return ResponseEntity.ok(new PageResponse<>(
        resultado.getContent().stream().map(HospedeResponse::new).collect(toList()),
        resultado.getTotalElements(),
        resultado.getTotalPages(),
        pagina,
        tamanho));
  }
}