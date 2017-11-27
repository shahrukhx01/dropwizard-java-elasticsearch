package com.wesa.elasticsearch.search.managed;

import io.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 */
public class StartupListener implements Managed {

  private final static Logger logger = LoggerFactory.getLogger(StartupListener.class);

  public void start() throws Exception {
    logger.info("StartupListener start");
  }

  public void stop() throws Exception {
    logger.info("StartupListener stop");
  }
}
