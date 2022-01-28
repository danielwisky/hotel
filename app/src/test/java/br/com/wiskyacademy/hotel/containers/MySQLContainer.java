package br.com.wiskyacademy.hotel.containers;

import static java.lang.String.format;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.Base58;

public class MySQLContainer extends GenericContainer<MySQLContainer> {

  private static final int MYSQL_PORT = 3306;
  private static final String MYSQL_DATABASE = "MYSQL_DATABASE";
  private static final String MYSQL_USER = "MYSQL_USER";
  private static final String MYSQL_PASSWORD = "MYSQL_PASSWORD";
  private static final String MYSQL_ROOT_PASSWORD = "MYSQL_ROOT_PASSWORD";
  private static final String MYSQL_URL_PATTERN = "jdbc:mysql://%s:%s/%s?useSSL=false";

  private final String mysqlDBName;

  public MySQLContainer(
      final String image, final String database, final String user, final String password) {

    super("mysql:" + image);
    this.mysqlDBName = database;

    addEnv(MYSQL_DATABASE, mysqlDBName);
    addEnv(MYSQL_USER, user);
    addEnv(MYSQL_PASSWORD, password);
    addEnv(MYSQL_ROOT_PASSWORD, password);

    withNetworkAliases("mysql-" + Base58.randomString(6));
    withExposedPorts(MYSQL_PORT);
    waitingFor(Wait.forListeningPort());

    System.setProperty(MYSQL_USER, user);
    System.setProperty(MYSQL_PASSWORD, password);
  }

  public String getMySQLUri() {
    return format(MYSQL_URL_PATTERN, getContainerIpAddress(), getFirstMappedPort(), mysqlDBName);
  }
}