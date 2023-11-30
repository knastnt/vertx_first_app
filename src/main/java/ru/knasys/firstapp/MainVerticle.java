package ru.knasys.firstapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.knasys.firstapp.web.AssetsRestApi;

public class MainVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  public static final int PORT = 8888;
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    final Router restApi = Router.router(vertx);
    AssetsRestApi.attach(restApi);

    vertx.createHttpServer()
      .requestHandler(restApi)
      .exceptionHandler(throwable -> {
        LOG.error("HTTP Server error:", throwable);
      })
      .listen(PORT)
      .onFailure(startPromise::fail)
      .onSuccess(httpServer -> {
        LOG.debug("Http server started. Listening on port: {}", httpServer.actualPort());
        startPromise.complete();
      });
  }
}
