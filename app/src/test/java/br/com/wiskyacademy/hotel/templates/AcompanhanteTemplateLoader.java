package br.com.wiskyacademy.hotel.templates;

import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_OUTRO_NOME_E_DOCUMENTO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_SEM_ID;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.wiskyacademy.hotel.domains.Acompanhante;
import br.com.wiskyacademy.hotel.domains.Hospede;
import java.time.LocalDate;

public class AcompanhanteTemplateLoader implements TemplateLoader {

  @Override
  public void load() {
    Fixture.of(Acompanhante.class).addTemplate(VALIDO.name(), new Rule() {{
          add("id", random(Integer.class, range(1, 100)));
          add("nome", name());
          add("documento", regex("\\d{11}"));
          add("dataNascimento", LocalDate.of(2000, 1, 1));
          add("hospede", one(Hospede.class, VALIDO.name()));
        }})
        .addTemplate(VALIDO_SEM_ID.name()).inherits(VALIDO.name(), new Rule() {{
          add("id", null);
          add("hospede", one(Hospede.class, VALIDO_SEM_ID.name()));
        }})
        .addTemplate(VALIDO_OUTRO_NOME_E_DOCUMENTO.name()).inherits(VALIDO_SEM_ID.name(), new Rule() {{
          add("nome", "Um nome qualquer");
          add("documento", "N/I");
        }});
  }
}