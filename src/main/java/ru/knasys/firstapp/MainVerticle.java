package ru.knasys.firstapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import java.time.LocalTime;

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    //exec1();
    exec2();
  }

  private static void exec1() throws InterruptedException {
    Promise<String> promise = Promise.promise();

    Vertx.vertx().setTimer(1000, event -> {
      System.out.println("Complete promice at " + LocalTime.now());
      promise.complete("result in " + LocalTime.now());
    });

    Thread.sleep(5000L);

    Future<String> future = promise.future();

    System.out.println("Make future on " + LocalTime.now());
    future.onComplete(event -> System.out.println("Result: " + event));
  }

  private static void exec2() {
    Promise<Void> p1 = Promise.<Void>promise();
    Promise<Void> p2 = Promise.<Void>promise();
    Promise<Void> p3 = Promise.<Void>promise();

    Future<Void> one = p1.future();
    Future<Void> two = p2.future();
    Future<Void> three = p3.future();

    Future.join(one, two, three)
      .onFailure(event -> System.out.println("!!!!!!!!!!!!!!!!! fail"))
      .onSuccess(event -> System.out.println("!!!!!!!!!!!!!!!!! success"));

    p1.complete();
    p2.fail("");
    p3.complete();
  }
}
