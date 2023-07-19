package ru.knasys.firstapp._010_event_bus.request_response;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseExample {
  private static final Logger log = LoggerFactory.getLogger(RequestResponseExample.class);

  public static void main(String[] args) {
    Vertx v = Vertx.vertx();
    v.deployVerticle(new RequestVerticle());
    v.deployVerticle(new ResponseVerticle());
  }

  public static class RequestVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      EventBus eventBus = vertx.eventBus();
      eventBus.<String>request("someAddress", "Hello request!", reply -> {
        log.debug(reply.result().body());
      });
    }
  }
  public static class ResponseVerticle extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      EventBus eventBus = vertx.eventBus();
      eventBus.<String>consumer("someAddress", event -> {
        log.debug(event.body());
        event.reply("Hi!");
      });
    }
  }
}
