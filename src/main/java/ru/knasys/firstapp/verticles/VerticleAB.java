package ru.knasys.firstapp.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

public class VerticleAB extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    if (true) throw new RuntimeException("unexpected!!!"); //куда-то проглатывается
    System.out.println("VerticleAB started Thread: " + Thread.currentThread().getName());
    startPromise.complete();
  }

  @Override
  public void stop(Promise<Void> stopPromise) throws Exception {
    System.out.println("VerticleAB stopped");
    stopPromise.complete();
  }
}
