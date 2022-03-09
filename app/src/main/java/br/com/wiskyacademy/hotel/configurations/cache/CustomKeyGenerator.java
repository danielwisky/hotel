package br.com.wiskyacademy.hotel.configurations.cache;

import java.lang.reflect.Method;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

public class CustomKeyGenerator implements KeyGenerator {

  public Object generate(final Object target, final Method method, final Object... params) {
    return target.getClass().getSimpleName()
        + "_"
        + method.getName()
        + "_"
        + StringUtils.arrayToDelimitedString(params, "_");
  }
}
