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

  private static final String DB_USER = "test";
  private static final String DB_PASSWORD = "test";

  private final String mysqlDBName;

  public MySQLContainer(final String mysqlVersion, final String mysqlDBName) {
    super("mysql:" + mysqlVersion);
    this.mysqlDBName = mysqlDBName;

    addEnv(MYSQL_DATABASE, mysqlDBName);
    addEnv(MYSQL_USER, DB_USER);
    addEnv(MYSQL_PASSWORD, DB_PASSWORD);
    addEnv(MYSQL_ROOT_PASSWORD, DB_PASSWORD);

    withNetworkAliases("mysql-" + Base58.randomString(6));
    withExposedPorts(MYSQL_PORT);
    waitingFor(Wait.forListeningPort());

    System.setProperty(MYSQL_USER, DB_USER);
    System.setProperty(MYSQL_PASSWORD, DB_PASSWORD);
  }

  public String getMySQLUri() {
    return format(MYSQL_URL_PATTERN, getContainerIpAddress(), getFirstMappedPort(), mysqlDBName);
  }
}