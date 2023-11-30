package ru.knasys.firstapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import ru.knasys.firstapp.web.AssetsRestApi;

@Slf4j
public class MainVerticle extends AbstractVerticle {
  public static final int PORT = 8888;
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    final Router restApi = Router.router(vertx);
    AssetsRestApi.attach(restApi);

    vertx.createHttpServer()
      .requestHandler(restApi)
      .exceptionHandler(throwable -> {
        log.error("HTTP Server error:", throwable);
      })
      .listen(PORT)
      .onFailure(startPromise::fail)
      .onSuccess(httpServer -> {
        log.debug("Http server started. Listening on port: {}", httpServer.actualPort());
        startPromise.complete();
      });
  }
}
