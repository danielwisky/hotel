package br.com.wiskyacademy.hotel.containers;

import static java.lang.String.format;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.Base58;

public class MySQLContainer extends GenericContainer<MySQLContainer> {

  private static final int MYSQL_PORT = 3306;
  private static final String MYSQL_URL_PATTERN = "jdbc:mysql://%s:%s/%s?useSSL=false";

  private final String mysqlDBName;

  public MySQLContainer(final String mysqlVersion, final String mysqlDBName) {
    super("mysql:" + mysqlVersion);
    this.mysqlDBName = mysqlDBName;

    addEnv("MYSQL_DATABASE", mysqlDBName);
    addEnv("MYSQL_USER", "test");
    addEnv("MYSQL_PASSWORD", "test");
    addEnv("MYSQL_ROOT_PASSWORD", "test");

    withNetworkAliases("mysql-" + Base58.randomString(6));
    withExposedPorts(MYSQL_PORT);
    waitingFor(Wait.forListeningPort());
  }

  public String getMySQLUri() {
    return format(MYSQL_URL_PATTERN, getContainerIpAddress(), getFirstMappedPort(), mysqlDBName);
  }
}