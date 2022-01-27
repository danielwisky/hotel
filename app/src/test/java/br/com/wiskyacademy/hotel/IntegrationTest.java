package br.com.wiskyacademy.hotel;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.com.wiskyacademy.hotel.containers.MySQLContainerConfiguration;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {HotelApplication.class}, webEnvironment = RANDOM_PORT)
@ActiveProfiles("test")
public abstract class IntegrationTest {

  @ClassRule
  public static MySQLContainerConfiguration mySQLContainerConfiguration =
      MySQLContainerConfiguration.getInstance();

  @BeforeClass
  public static synchronized void setup() {
    FixtureFactoryLoader.loadTemplates("br.com.wiskyacademy.hotel.templates");
  }
}
