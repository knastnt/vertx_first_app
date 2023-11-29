package ru.knasys.firstapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Vertx.vertx().createHttpServer()
      .requestHandler(httpServerRequest -> {
        LOG.info("Request to http server: {}", httpServerRequest);
        httpServerRequest.response()
          .putHeader("content-type", "text/plain")
          .end("Hello from Vert.x!");
      })
      .listen(8888)
      .onFailure(startPromise::fail)
      .onSuccess(httpServer -> {
        LOG.debug("Http server started. Listening on port: {}", httpServer.actualPort());
        startPromise.complete();
      });
  }
}
