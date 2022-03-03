package br.com.wiskyacademy.hotel;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public abstract class IntegrationTest {

  @BeforeAll
  public static synchronized void setup() {
    FixtureFactoryLoader.loadTemplates("br.com.wiskyacademy.hotel.templates");
  }
}
