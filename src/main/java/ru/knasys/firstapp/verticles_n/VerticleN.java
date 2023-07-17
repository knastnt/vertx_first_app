package ru.knasys.firstapp.verticles_n;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import ru.knasys.firstapp.verticles.VerticleAA;
import ru.knasys.firstapp.verticles.VerticleAB;

public class VerticleN extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    System.out.println("VerticleN started. Thread: " + Thread.currentThread().getName() + " with config: " + config().toString());
    startPromise.complete();
  }
}
