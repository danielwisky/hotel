package br.com.wiskyacademy.hotel.gateways.inputs.http;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.ID;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.OK;

import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.AcompanhanteDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.AcompanhanteRequest;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.FiltroAcompanhanteRequest;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response.AcompanhanteResponse;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response.PageResponse;
import br.com.wiskyacademy.hotel.usecases.AlterarAcompanhante;
import br.com.wiskyacademy.hotel.usecases.CriarAcompanhante;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/hospedes/{hospedeId}/acompanhantes")
public class AcompanhanteController {

  private final CriarAcompanhante criarAcompanhante;
  private final AlterarAcompanhante alterarAcompanhante;

  private final AcompanhanteDatabaseGateway acompanhanteDatabaseGateway;

  @PostMapping
  @ResponseStatus(OK)
  @CacheEvict(cacheNames = {"buscarAcompanhante", "pesquisarAcompanhantes"}, allEntries = true)
  public ResponseEntity<AcompanhanteResponse> criar(
      @PathVariable final Integer hospedeId,
      @RequestBody @Valid final AcompanhanteRequest acompanhanteRequest) {

    return ResponseEntity.ok(new AcompanhanteResponse(
        criarAcompanhante.executar(hospedeId, acompanhanteRequest.toDomain())));
  }

  @PutMapping("/{acompanhanteId}")
  @ResponseStatus(OK)
  @CacheEvict(cacheNames = {"buscarAcompanhante", "pesquisarAcompanhantes"}, allEntries = true)
  public ResponseEntity<AcompanhanteResponse> editar(
      @PathVariable final Integer hospedeId,
      @PathVariable final Integer acompanhanteId,
      @RequestBody @Valid final AcompanhanteRequest acompanhanteRequest) {

    return ResponseEntity.ok(new AcompanhanteResponse(
        alterarAcompanhante.executar(hospedeId, acompanhanteId, acompanhanteRequest.toDomain())));
  }

  @GetMapping("/{acompanhanteId}")
  @ResponseStatus(OK)
  @ResponseBody
  @Cacheable(value = "buscarAcompanhante", keyGenerator = "customKeyGenerator")
  public AcompanhanteResponse buscar(
      @PathVariable final Integer hospedeId,
      @PathVariable final Integer acompanhanteId) {
    return acompanhanteDatabaseGateway
        .findByHospedeIdAndAcompanhanteId(hospedeId, acompanhanteId)
        .map(AcompanhanteResponse::new)
        .orElseThrow(ResourceNotFoundException::new);
  }

  @GetMapping
  @ResponseStatus(OK)
  @ResponseBody
  @Cacheable(value = "pesquisarAcompanhantes", keyGenerator = "customKeyGenerator")
  public PageResponse<AcompanhanteResponse> pesquisar(
      @PathVariable final Integer hospedeId,
      final FiltroAcompanhanteRequest filtro,
      @RequestParam(defaultValue = "0") final Integer pagina,
      @RequestParam(defaultValue = "20") final Integer tamanho) {
    final Page<Acompanhante> resultado = acompanhanteDatabaseGateway.search(
        filtro.toDomain(hospedeId), PageRequest.of(pagina, tamanho, DESC, ID));

    return new PageResponse<>(
        resultado.getContent().stream().map(AcompanhanteResponse::new).collect(toList()),
        resultado.getTotalElements(),
        resultado.getTotalPages(),
        pagina,
        tamanho);
  }
}
