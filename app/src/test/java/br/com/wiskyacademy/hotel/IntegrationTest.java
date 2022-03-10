package br.com.wiskyacademy.hotel;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public abstract class IntegrationTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @BeforeAll
  public static synchronized void setup() {
    FixtureFactoryLoader.loadTemplates("br.com.wiskyacademy.hotel.templates");
  }

  @BeforeEach
  public void clearData() {
    JdbcTestUtils.deleteFromTables(jdbcTemplate,
        "acompanhante_hospedagem",
        "hospedagem",
        "acompanhante",
        "endereco",
        "hospede",
        "acomodacao");
  }
}
