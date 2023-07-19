package ru.knasys.firstapp._009_worker_threads;

import io.vertx.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerExampleVerticle extends AbstractVerticle {
  private static final Logger log = LoggerFactory.getLogger(WorkerExampleVerticle.class);

  public static void main(String[] args) {
    Vertx v = Vertx.vertx();
    v.deployVerticle(new WorkerExampleVerticle());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(new WorkerVerticle(), new DeploymentOptions()
      .setWorker(true)
      .setWorkerPoolSize(1)
      .setWorkerPoolName("My-worker-verticle")
    ).onComplete(event -> log.debug("completed"));
  }


  public static class WorkerVerticle extends AbstractVerticle {
    private static final Logger log = LoggerFactory.getLogger(WorkerVerticle.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      log.debug("Deployed as worker verticle");
      log.debug("Blocking operation started");
      Thread.sleep(2000);
      log.debug("Blocking operation is done");
      startPromise.complete();
    }
  }
}
