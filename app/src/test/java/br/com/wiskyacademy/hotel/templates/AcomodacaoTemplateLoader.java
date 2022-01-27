package br.com.wiskyacademy.hotel.templates;

import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_SEM_ID;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.wiskyacademy.hotel.domains.Acomodacao;

public class AcomodacaoTemplateLoader implements TemplateLoader {

  @Override
  public void load() {
    Fixture.of(Acomodacao.class).addTemplate(VALIDO.name(), new Rule() {{
      add("id", random(Integer.class, range(1, 100)));
      add("nome", "Quarto ${id}");
      add("descricao", "${nome} com capacidade para ${capacidade} pessoas");
      add("capacidade", random(Integer.class, range(1, 10)));
      add("preco", 200.0f);
    }}).addTemplate(VALIDO_SEM_ID.name()).inherits(VALIDO.name(), new Rule() {{
      add("id", null);
      add("nome", "Quarto s/ id");
    }});
  }
}