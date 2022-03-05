package br.com.wiskyacademy.hotel.templates;

import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.AcompanhanteRequest;
import java.time.LocalDate;

public class AcompanhanteRequestTemplateLoader implements TemplateLoader {

  @Override
  public void load() {
    Fixture.of(AcompanhanteRequest.class).addTemplate(VALIDO.name(), new Rule() {{
      add("nome", name());
      add("documento", regex("\\d{11}"));
      add("dataNascimento", LocalDate.of(1990, 1, 1));
    }});
  }
}