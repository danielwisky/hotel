package br.com.wiskyacademy.hotel.templates;

import static br.com.six2six.fixturefactory.Fixture.of;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_CAPACIDADE_ALTA;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_CAPACIDADE_BAIXA;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_OUTRO_NOME_E_DESCRICAO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_PRECO_ALTO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_PRECO_BAIXO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_SEM_ID;

import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.com.wiskyacademy.hotel.domains.Acomodacao;

public class AcomodacaoTemplateLoader implements TemplateLoader {

  @Override
  public void load() {
    of(Acomodacao.class)
        .addTemplate(VALIDO.name(), new Rule() {{
          add("id", random(Integer.class, range(1, 100)));
          add("nome", "Quarto ${id}");
          add("descricao", "${nome} com capacidade para ${capacidade} pessoas");
          add("capacidade", random(Integer.class, range(4, 10)));
          add("preco", 200.0f);
        }})
        .addTemplate(VALIDO_SEM_ID.name()).inherits(VALIDO.name(), new Rule() {{
          add("id", null);
          add("nome", "Quarto s/ id");
        }})
        .addTemplate(VALIDO_OUTRO_NOME_E_DESCRICAO.name()).inherits(VALIDO_SEM_ID.name(), new Rule() {{
          add("nome", "Um nome qualquer");
          add("descricao", "Uma descrição qualquer");
        }})
        .addTemplate(VALIDO_CAPACIDADE_BAIXA.name()).inherits(VALIDO_SEM_ID.name(), new Rule() {{
          add("capacidade", 1);
        }})
        .addTemplate(VALIDO_CAPACIDADE_ALTA.name()).inherits(VALIDO_SEM_ID.name(), new Rule() {{
          add("capacidade", 20);
        }})
        .addTemplate(VALIDO_PRECO_BAIXO.name()).inherits(VALIDO_SEM_ID.name(), new Rule() {{
          add("preco", 1.99f);
        }})
        .addTemplate(VALIDO_PRECO_ALTO.name()).inherits(VALIDO_SEM_ID.name(), new Rule() {{
          add("preco", 1500.0f);
        }});
  }
}