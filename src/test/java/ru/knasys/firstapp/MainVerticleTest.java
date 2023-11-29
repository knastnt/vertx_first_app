package ru.knasys.firstapp;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
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
  void test_map_null(Vertx vertx, VertxTestContext testContext) {
    Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.complete();
      LOG.debug("Complete.");
      testContext.completeNow();
    });
    LOG.debug("End");
    promise.future()
      .map(resultString -> {
        LOG.debug("Map string to JsonObject");
        return new JsonObject().put("res", resultString);
      })
      .onSuccess(event -> {
        LOG.debug(event.encode());
      });
  }

  @Test
  void test_map_notNull(Vertx vertx, VertxTestContext testContext) {
    Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.complete("Hello!!!");
      LOG.debug("Complete.");
      testContext.completeNow();
    });
    LOG.debug("End");
    promise.future()
      .map(resultString -> {
        LOG.debug("Map string to JsonObject");
        return new JsonObject().put("res", resultString);
      })
      .onSuccess(event -> {
        LOG.debug(event.encode());
      });
  }

  @Test
  void test_mapEmpty(Vertx vertx, VertxTestContext testContext) {
    Promise<String> promise = Promise.promise();
    LOG.debug("Start");
    vertx.setTimer(500, id -> {
      promise.complete("Hello!!!");
      LOG.debug("Complete.");
      testContext.completeNow();
    });
    LOG.debug("End");
    promise.future()
      .mapEmpty()//если нам надо только знать удался или провалился промис
      .onSuccess(thisAlwaysWillBeNull -> {
        LOG.debug(String.valueOf(thisAlwaysWillBeNull));
      });
  }

  @Test
  void future_coordination(Vertx vertx, VertxTestContext testContext) {
    vertx.createHttpServer()
      .requestHandler(httpServerRequest -> {
        LOG.info("Request to http server: {}", httpServerRequest);
      })
      .listen(10_000)
      .compose(httpServer -> {
        LOG.debug("Another task");
        return Future.succeededFuture(httpServer);
      })
      .compose(httpServer -> {
        LOG.debug("Even more task");
        Promise<HttpServer> promise = Promise.promise();
        promise.complete(httpServer);
        return promise.future();
      })
      .onFailure(testContext::failNow)
      .onSuccess(httpServer -> {
        LOG.debug("Http server started. Listening on port: {}", httpServer.actualPort());
        testContext.completeNow();
      });
  }
}
