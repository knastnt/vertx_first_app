package ru.knasys.firstapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

public class MainVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Vertx.vertx().createHttpServer()
      .requestHandler(httpServerRequest -> {
        LOG.info("Request to http server: {}", httpServerRequest);
      })
      .listen(8888)
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
      .onFailure(startPromise::fail)
      .onSuccess(httpServer -> {
        LOG.debug("Http server started. Listening on port: {}", httpServer.actualPort());
        startPromise.complete();
      });
  }
}
