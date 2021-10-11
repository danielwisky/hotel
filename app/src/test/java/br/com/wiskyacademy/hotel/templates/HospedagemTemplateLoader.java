package br.com.wiskyacademy.hotel.templates;

import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.wiskyacademy.hotel.domains.Acomodacao;
import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.Hospedagem;
import br.com.wiskyacademy.hotel.domains.Hospede;
import br.com.wiskyacademy.hotel.domains.StatusHospedagem;
import java.time.LocalDate;

public class HospedagemTemplateLoader implements TemplateLoader {

  @Override
  public void load() {
    Fixture.of(Hospedagem.class).addTemplate(VALIDO.name(), new Rule() {{
      add("id", random(Integer.class, range(1, 100)));
      add("acomodacao", one(Acomodacao.class, VALIDO.name()));
      add("hospede", one(Hospede.class, VALIDO.name()));
      add("acompanhantes", has(2).of(Acompanhante.class, VALIDO.name()));
      add("valor", random(Float.class, range(10.0, 100.0)));
      add("dataEntrada", LocalDate.of(2021, 8, 10));
      add("dataSaida", LocalDate.of(2021, 8, 12));
      add("status", StatusHospedagem.RESERVADO);
    }});
  }
}