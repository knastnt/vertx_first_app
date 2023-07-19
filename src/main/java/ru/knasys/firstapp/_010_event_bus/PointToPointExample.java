package ru.knasys.firstapp._010_event_bus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PointToPointExample {
  private static final Logger log = LoggerFactory.getLogger(PointToPointExample.class);

  public static void main(String[] args) {
    Vertx v = Vertx.vertx();
    //создадим 2 инстанса ресивера, сообщение будет прилетать в одного из них
    v.deployVerticle(Receiver.class.getName(), new DeploymentOptions().setInstances(2)).onComplete(event -> {
      v.deployVerticle(new Sender()); //Чтобы сообщение сгенерировалось когда уже существует Receiver
    });

  }

  public static class Sender extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      EventBus eventBus = vertx.eventBus();
      vertx.setPeriodic(1000, event -> {
        eventBus.<String>send(Sender.class.getName() + ".someAddress", "Hello world!");
      });
      startPromise.complete();
    }
  }
  public static class Receiver extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      EventBus eventBus = vertx.eventBus();
      eventBus.<String>consumer(Sender.class.getName() + ".someAddress", message -> {
        log.debug(Integer.toHexString(this.hashCode()) + " receiver: " + message.body());
      });
      startPromise.complete();
    }
  }
}
