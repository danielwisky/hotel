package br.com.wiskyacademy.hotel.templates;

import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO;
import static br.com.wiskyacademy.hotel.templates.FixtureCoreTemplates.VALIDO_SEM_ID;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_TWO;

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
          add("dataEntrada", LocalDate.now().minusDays(INTEGER_ONE));
          add("dataSaida", LocalDate.now().plusDays(INTEGER_TWO));
          add("status", StatusHospedagem.RESERVADO);
        }})
        .addTemplate(VALIDO_SEM_ID.name()).inherits(VALIDO.name(), new Rule() {{
          add("id", null);
          add("acomodacao", one(Acomodacao.class, VALIDO_SEM_ID.name()));
          add("hospede", one(Hospede.class, VALIDO_SEM_ID.name()));
          add("acompanhantes", has(2).of(Acompanhante.class, VALIDO_SEM_ID.name()));
          add("status", StatusHospedagem.PAGO);
        }});
  }
}