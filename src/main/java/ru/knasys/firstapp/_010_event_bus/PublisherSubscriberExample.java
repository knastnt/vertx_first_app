package ru.knasys.firstapp._010_event_bus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class PublisherSubscriberExample {
  private static final Logger log = LoggerFactory.getLogger(PublisherSubscriberExample.class);

  public static void main(String[] args) {
    Vertx v = Vertx.vertx();
    v.deployVerticle(new Subscriber1());
    v.deployVerticle(new Subscriber2());

    v.deployVerticle(new Publisher());
  }

  public static class Publisher extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      EventBus eventBus = vertx.eventBus();
      vertx.setPeriodic(Duration.ofSeconds(1).toMillis(), event -> {
        eventBus.<String>publish(Publisher.class.getName() + ".someAddress", "Message for everyone!");
      });
      startPromise.complete();
    }
  }
  public static class Subscriber1 extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      EventBus eventBus = vertx.eventBus();
      eventBus.<String>consumer(Publisher.class.getName() + ".someAddress", message -> {
        log.debug("Subscriber1: " + message.body());
      });
      startPromise.complete();
    }
  }
  public static class Subscriber2 extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      EventBus eventBus = vertx.eventBus();
      eventBus.<String>consumer(Publisher.class.getName() + ".someAddress", message -> {
        log.debug("Subscriber2: " + message.body());
      });
      startPromise.complete();
    }
  }
}
