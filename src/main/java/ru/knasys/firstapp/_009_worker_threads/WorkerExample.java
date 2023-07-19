package ru.knasys.firstapp._009_worker_threads;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerExample extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(WorkerExample.class);

  public static void main(String[] args) {
    Vertx v = Vertx.vertx();
    v.deployVerticle(new WorkerExample());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Future<Object> future = vertx.executeBlocking(event -> {
      log.debug("Start executing a blocking code");
      try {
        Thread.sleep(2000);
      } catch (InterruptedException e) {throw new RuntimeException(e);}
//      if (true) throw new RuntimeException("ыыыыыыа");
      log.debug("End executing a blocking code");
      event.complete();
    });
    future.onComplete(event -> log.debug("complete blocking code"));
    future.onSuccess(event -> log.debug("success blocking code"));
    future.onFailure(throwable -> log.error("fail blocking code", throwable));

    startPromise.complete();
  }
}
