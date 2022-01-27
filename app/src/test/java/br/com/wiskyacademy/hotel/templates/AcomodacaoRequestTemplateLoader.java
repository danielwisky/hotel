package br.com.wiskyacademy.hotel.templates;

import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.wiskyacademy.hotel.gateways.inputs.http.resources.AcomodacaoRequest;

public class AcomodacaoRequestTemplateLoader implements TemplateLoader {

  @Override
  public void load() {
    Fixture.of(AcomodacaoRequest.class).addTemplate(VALIDO.name(), new Rule() {{
      add("nome", "Quarto Duplo");
      add("descricao", "Quarto Duplo com capacidade para ${capacidade} pessoas");
      add("capacidade", random(Integer.class, range(1, 10)));
      add("preco", random(Float.class, range(10.0, 100.0)));
    }});
  }
}