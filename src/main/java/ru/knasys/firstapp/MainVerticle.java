package ru.knasys.firstapp;

import io.vertx.core.*;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

public class MainVerticle extends AbstractVerticle {
  public static void main(String[] args) throws Exception {
    MainVerticle mainVerticle = new MainVerticle();
    Vertx vertx1 = Vertx.vertx();
    vertx1.deployVerticle(mainVerticle);
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    int port = 8888;

    startPromise.future()
      .onSuccess(event -> System.out.println("HTTP server started on port " + port))
      .onFailure(Throwable::printStackTrace)
      .onFailure(event -> vertx.close());

    vertx.createHttpServer().requestHandler(req -> {
      req.response()
        .putHeader("content-type", "text/plain")
        .end("Hello world!");
    }).listen(port, http -> {
      if (http.succeeded()) {
        startPromise.complete();
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
