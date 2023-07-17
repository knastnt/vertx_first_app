package ru.knasys.firstapp.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

public class MainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx v = Vertx.vertx();

    v.deployVerticle(new MainVerticle())
      .onComplete(event ->
        System.out.println("init completed"));
  }
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Future<String> futureA = vertx.deployVerticle(new VerticleA());
    Future<String> futureB = vertx.deployVerticle(new VerticleB());

    System.out.println("MainVerticle started");
    startPromise.complete();
  }
}
