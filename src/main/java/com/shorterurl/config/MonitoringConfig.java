package com.shorterurl.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitoringConfig {

  @Autowired
  public void configureRegistry(MeterRegistry registry) {
    registry.config().commonTags("application", "shorterurl");
  }
}
