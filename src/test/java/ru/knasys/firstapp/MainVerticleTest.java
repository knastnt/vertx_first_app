package ru.knasys.firstapp;

import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(VertxExtension.class)
class MainVerticleTest {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticleTest.class);
  @Test
  void test(Vertx vertx, VertxTestContext testContext) {
    Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(2500, id -> {
      promise.complete();
      LOG.debug("Complete");
      testContext.completeNow();
    });
    LOG.debug("End");
  }
}
