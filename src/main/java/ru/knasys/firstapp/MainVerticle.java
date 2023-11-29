package ru.knasys.firstapp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

public class MainVerticle extends AbstractVerticle {
  private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
  public static void main(String[] args) throws Exception {
    exec1();
    exec2();
  }

  private static void exec1() throws InterruptedException {
    Promise<String> promise = Promise.promise();

    Vertx.vertx().setTimer(1000, event -> {
      LOG.debug("Complete promice at " + LocalTime.now());
      promise.complete("result in " + LocalTime.now());
    });

    Thread.sleep(5000L);

    Future<String> future = promise.future();

    LOG.debug("Make future on " + LocalTime.now());
    future.onComplete(event -> LOG.debug("Result: " + event));
  }

  private static void exec2() {
    Promise<Void> p1 = Promise.<Void>promise();
    Promise<Void> p2 = Promise.<Void>promise();
    Promise<Void> p3 = Promise.<Void>promise();

    Future<Void> one = p1.future();
    Future<Void> two = p2.future();
    Future<Void> three = p3.future();

    Future.join(one, two, three)
      .onFailure(event -> LOG.debug("!!!!!!!!!!!!!!!!! fail"))
      .onSuccess(event -> LOG.debug("!!!!!!!!!!!!!!!!! success"));

    p1.complete();
    p2.fail("");
//    p3.complete();

    //сейчас Future.join не сработает, т.к. p3 не закончится, а вот выполнение программы закончится. Вся логика делается
    // в одном main thread, а должна происходить в эвент лупе. Поэтому надо стартовать vertx - ланчером
  }
}
