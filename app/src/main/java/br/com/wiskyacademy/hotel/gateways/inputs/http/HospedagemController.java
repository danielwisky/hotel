package br.com.wiskyacademy.hotel.gateways.inputs.http;

import static br.com.wiskyacademy.hotel.gateways.outputs.mysql.entities.AcomodacaoEntity_.ID;
import static java.util.stream.Collectors.toList;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.OK;

import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.exceptions.ResourceNotFoundException;
import br.com.wiskyacademy.hotel.gateways.HospedagemDatabaseGateway;
import br.com.wiskyacademy.hotel.gateways.inputs.http.adapters.HospedagemAdapter;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.FiltroHospedagemRequest;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.ReservaHospedagemRequest;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response.HospedagemResponse;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.response.PageResponse;
import br.com.wiskyacademy.hotel.usecases.CheckIn;
import br.com.wiskyacademy.hotel.usecases.CheckOut;
import br.com.wiskyacademy.hotel.usecases.ReservarHospedagem;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/hospedagens")
public class HospedagemController {

  private final HospedagemDatabaseGateway hospedagemDatabaseGateway;
  private final HospedagemAdapter hospedagemAdapter;
  private final ReservarHospedagem reservarHospedagem;
  private final CheckIn checkIn;
  private final CheckOut checkOut;

  @PostMapping
  @ResponseStatus(OK)
  @ApiOperation(value = "Reservar uma hospedagem")
  @CacheEvict(cacheNames = {"buscarHospedagem", "pesquisarHospedagens"}, allEntries = true)
  public ResponseEntity<HospedagemResponse> reservar(
      @RequestBody @Valid final ReservaHospedagemRequest reservaHospedagemRequest) {
    final Hospedagem hospedagem = hospedagemAdapter.to(reservaHospedagemRequest);
    return ResponseEntity.ok(
        new HospedagemResponse(reservarHospedagem.executar(hospedagem)));
  }

  @PutMapping("/{id}/check-in")
  @ResponseStatus(OK)
  @ApiOperation(value = "Realizar check-in de uma hospedagem")
  @CacheEvict(cacheNames = {"buscarHospedagem", "pesquisarHospedagens"}, allEntries = true)
  public ResponseEntity<HospedagemResponse> checkIn(@PathVariable final Integer id) {
    return ResponseEntity.ok(new HospedagemResponse(checkIn.executar(id)));
  }

  @PutMapping("/{id}/check-out")
  @ResponseStatus(OK)
  @ApiOperation(value = "Realizar check-out de uma hospedagem")
  @CacheEvict(cacheNames = {"buscarHospedagem", "pesquisarHospedagens"}, allEntries = true)
  public ResponseEntity<HospedagemResponse> checkOut(@PathVariable final Integer id) {
    return ResponseEntity.ok(new HospedagemResponse(checkOut.executar(id)));
  }

  @GetMapping("/{id}")
  @ResponseStatus(OK)
  @ResponseBody
  @ApiOperation(value = "Buscar um hospedagens por id")
  @Cacheable(value = "buscarHospedagem", keyGenerator = "customKeyGenerator")
  public HospedagemResponse buscar(@PathVariable final Integer id) {
    return hospedagemDatabaseGateway
        .findById(id)
        .map(HospedagemResponse::new)
        .orElseThrow(ResourceNotFoundException::new);
  }

  @GetMapping
  @ResponseStatus(OK)
  @ResponseBody
  @ApiOperation(value = "Pesquisa hospedagens cadastrados")
  @Cacheable(value = "pesquisarHospedagens", keyGenerator = "customKeyGenerator")
  public PageResponse<HospedagemResponse> pesquisar(
      final FiltroHospedagemRequest filtro,
      @RequestParam(defaultValue = "0") final Integer pagina,
      @RequestParam(defaultValue = "20") final Integer tamanho) {
    final Page<Hospedagem> resultado =
        hospedagemDatabaseGateway.search(filtro.toDomain(), of(pagina, tamanho, DESC, ID));

    return new PageResponse<>(
        resultado.getContent().stream().map(HospedagemResponse::new).collect(toList()),
        resultado.getTotalElements(),
        resultado.getTotalPages(),
        pagina,
        tamanho);
  }
}
