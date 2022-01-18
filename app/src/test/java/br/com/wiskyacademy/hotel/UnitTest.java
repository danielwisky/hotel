package br.com.wiskyacademy.hotel;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public abstract class UnitTest {

  @BeforeAll
  public static synchronized void setup() {
    FixtureFactoryLoader.loadTemplates("br.com.wiskyacademy.hotel.templates");
  }
}