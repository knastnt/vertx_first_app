package ru.knasys.firstapp.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleB extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("VerticleB started Thread: " + Thread.currentThread().getName());
    startPromise.complete();
  }
}
