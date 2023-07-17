package ru.knasys.firstapp.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;

public class VerticleA extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Future<String> futureAA = vertx.deployVerticle(new VerticleAA());
    Future<String> futureAB = vertx.deployVerticle(new VerticleAB());

    futureAB.onComplete(event -> vertx.undeploy(event.result()));
    System.out.println("VerticleA started Thread: " + Thread.currentThread().getName());
    startPromise.complete();
  }
}
