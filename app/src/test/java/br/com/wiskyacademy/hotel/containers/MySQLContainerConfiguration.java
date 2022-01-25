package br.com.wiskyacademy.hotel.containers;

import org.testcontainers.containers.MySQLContainer;

public class MySQLContainerConfiguration extends MySQLContainer {

  private static MySQLContainerConfiguration container;

  public static MySQLContainerConfiguration getInstance() {
    if (container == null) {
      container = new MySQLContainerConfiguration();
    }

    return container;
  }

  @Override
  public void start() {
    super.start();
  }
}
