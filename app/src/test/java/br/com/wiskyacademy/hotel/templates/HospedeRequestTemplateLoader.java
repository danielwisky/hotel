package br.com.wiskyacademy.hotel.templates;

import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.EnderecoRequest;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.request.HospedeRequest;
import java.time.LocalDate;

public class HospedeRequestTemplateLoader implements TemplateLoader {

  @Override
  public void load() {
    Fixture.of(HospedeRequest.class).addTemplate(VALIDO.name(), new Rule() {{
      add("nome", name());
      add("documento", regex("\\d{11}"));
      add("dataNascimento", LocalDate.of(1990, 1, 1));
      add("endereco", one(EnderecoRequest.class, VALIDO.name()));
      add("email", "${documento}@email.com");
      add("telefone", regex("(11) \\d{4}-\\d{4}"));
      add("celular", regex("(11) \\d{5}-\\d{4}"));
    }});
  }
}