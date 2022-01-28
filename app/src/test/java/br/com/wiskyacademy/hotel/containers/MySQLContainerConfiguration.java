package br.com.wiskyacademy.hotel.containers;


public class MySQLContainerConfiguration extends MySQLContainer {

  private static final String DOCKER_IMAGE = "5.7.37";
  private static final String MYSQL_DB_NAME = "hotel-test";
  private static final String MYSQL_DB_USER = "test";
  private static final String MYSQL_DB_PASS = "test";

  private static MySQLContainerConfiguration container;

  public MySQLContainerConfiguration() {
    super(DOCKER_IMAGE, MYSQL_DB_NAME, MYSQL_DB_USER, MYSQL_DB_PASS);
  }

  public static MySQLContainerConfiguration getInstance() {

    if (container == null) {
      container = new MySQLContainerConfiguration();
    }

    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("MYSQL_URI", container.getMySQLUri());
  }
}
