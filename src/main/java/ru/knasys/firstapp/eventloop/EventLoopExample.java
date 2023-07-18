package ru.knasys.firstapp.eventloop;

import io.vertx.core.*;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EventLoopExample extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(EventLoopExample.class);

  public static void main(String[] args) {
    Vertx v = Vertx.vertx(
      new VertxOptions()
        .setMaxEventLoopExecuteTime(3500)
        .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)

        .setBlockedThreadCheckInterval(500)
        .setBlockedThreadCheckIntervalUnit(TimeUnit.MILLISECONDS)

        .setEventLoopPoolSize(2) //default = 2 * CpuCoreSensor.availableProcessors()
    );
    v.deployVerticle(EventLoopExample.class.getName(), new DeploymentOptions().setInstances(4))
      .onComplete(event -> log.info("completed"))
      .onSuccess(event -> log.info("success"))
      .onFailure(event -> log.info("failure"))
    ;
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    startPromise.complete();
    Thread.sleep(5000L);
    System.out.println("done");
  }
}
