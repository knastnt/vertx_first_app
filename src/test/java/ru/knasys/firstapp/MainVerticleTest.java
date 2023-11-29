package ru.knasys.firstapp;

import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

  @Test
  void test_promiseProcessingInSameThread(Vertx vertx, VertxTestContext testContext) {
    AtomicReference<String> threadName = new AtomicReference<>();
    Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(2500, id -> {
      threadName.set(Thread.currentThread().getName());
      promise.complete();
      LOG.debug("Complete. Thread: {}", threadName.get());
      testContext.completeNow();
    });
    LOG.debug("End");
    promise.future()
      .onSuccess(event -> {
        LOG.debug("on success future. Thread: {}", Thread.currentThread().getName());
        assertEquals(threadName.get(), Thread.currentThread().getName());
      })
      .onFailure(testContext::failNow);
  }

  @Test
  void test_failed(Vertx vertx, VertxTestContext testContext) {
    Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(2500, id -> {
      promise.future();
      LOG.debug("Complete.");
      testContext.completeNow();
    });
    LOG.debug("End");
    promise.future()
      .onSuccess(testContext::failNow)
      .onFailure(event -> {
        LOG.debug("on expected future.");
      });
  }
}
