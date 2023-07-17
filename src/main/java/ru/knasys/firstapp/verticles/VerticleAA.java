package ru.knasys.firstapp.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;

public class VerticleAA extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("VerticleAA started Thread: " + Thread.currentThread().getName());
    startPromise.complete();
  }
}
