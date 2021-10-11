package br.com.wiskyacademy.hotel.templates;

import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.wiskyacademy.hotel.domains.Endereco;

public class EnderecoTemplateLoader implements TemplateLoader {

  @Override
  public void load() {
    Fixture.of(Endereco.class).addTemplate(VALIDO.name(), new Rule() {{
      add("id", random(Integer.class, range(1, 100)));
      add("cep", regex("\\d{5}-\\d{3}"));
      add("numero", regex("\\d{3}"));
      add("bairro", random("Botafogo", "Tijuca", "Vila Madalena", "Pinheiros"));
      add("cidade", random("Campos do Jordão", "Santo Antônio do Pinhal", "Petrópolis"));
      add("estado", random("São Paulo", "Rio de Janeiro"));
      add("complemento", random("casa", "apartamento"));
    }});
  }
}