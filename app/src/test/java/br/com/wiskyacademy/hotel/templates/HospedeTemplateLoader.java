package br.com.wiskyacademy.hotel.templates;

import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_OUTRO_NOME_E_DOCUMENTO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_SEM_ID;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.wiskyacademy.hotel.domains.Endereco;
import br.com.wiskyacademy.hotel.domains.Hospede;
import java.time.LocalDate;

public class HospedeTemplateLoader implements TemplateLoader {

  @Override
  public void load() {
    Fixture.of(Hospede.class).addTemplate(VALIDO.name(), new Rule() {{
          add("id", random(Integer.class, range(1, 1000)));
          add("nome", name());
          add("documento", regex("\\d{11}"));
          add("dataNascimento", LocalDate.of(1990, 1, 1));
          add("endereco", one(Endereco.class, VALIDO.name()));
          add("email", "${id}@email.com");
          add("telefone", regex("(11) \\d{4}-\\d{4}"));
          add("celular", regex("(11) \\d{5}-\\d{4}"));
          add("ativo", true);
        }})
        .addTemplate(VALIDO_SEM_ID.name()).inherits(VALIDO.name(), new Rule() {{
          add("id", null);
          add("email", "semid@email.com");
          add("endereco", one(Endereco.class, VALIDO_SEM_ID.name()));
        }})
        .addTemplate(VALIDO_OUTRO_NOME_E_DOCUMENTO.name())
        .inherits(VALIDO_SEM_ID.name(), new Rule() {{
          add("nome", "Um nome qualquer");
          add("documento", "N/I");
          add("email", "outro-email@email.com");
        }});
  }
}