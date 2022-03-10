package br.com.wiskyacademy.hotel.configurations.cache;

import java.lang.reflect.Method;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.interceptor.KeyGenerator;

public class CustomKeyGenerator implements KeyGenerator {

  public Object generate(final Object target, final Method method, final Object... params) {
    return target.getClass().getSimpleName()
        + "_"
        + method.getName()
        + "_"
        + StringUtils.join(params, "_");
  }
}
