package ru.knasys.firstapp._010_event_bus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.eventbus.impl.CodecManager;
import io.vertx.core.eventbus.impl.codecs.SerializableCodec;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingPongExample {
  private static final Logger log = LoggerFactory.getLogger(PingPongExample.class);

  public static void main(String[] args) {
    Vertx v = Vertx.vertx();
    v.deployVerticle(new Receiver()).onComplete(event -> {
      v.deployVerticle(new Sender()).onFailure(Throwable::printStackTrace);
    }).onFailure(Throwable::printStackTrace);

  }

  public static class Sender extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      EventBus eventBus = vertx.eventBus();
      eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
      vertx.setPeriodic(1000, 10000, event -> {
        Ping ping = new Ping(event.intValue());
        log.debug("Send object to event bus: {}", ping);
        eventBus.<Pong>request(Sender.class.getName() + ".someAddress", ping, reply -> {
          Pong pong = reply.result().body();
          log.debug("Catch reply: {}", pong);
        });
      });
      startPromise.complete();
    }
  }
  public static class Receiver extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      EventBus eventBus = vertx.eventBus();
      eventBus.registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
      eventBus.<Ping>consumer(Sender.class.getName() + ".someAddress", message -> {
        Ping ping = message.body();
        log.debug("Received: " + ping);
        Pong pong = new Pong("reply for " + ping.name, ping);
        message.reply(pong);
      });
      startPromise.complete();
    }
  }

  @ToString
  public static class Ping { //для передачи по event bus объект должен быть немутабельным, или нужно разбираться с методом transform в кодеке
    private Integer id;
    private String name;

    public Ping(Integer id) {
      this.id = id;
      name = Integer.toHexString(hashCode());
    }
  }

  @AllArgsConstructor
  @ToString
  public static class Pong { //для передачи по event bus объект должен быть немутабельным, или нужно разбираться с методом transform в кодеке
    private String name;
    private Ping ping;
  }

  public static class LocalMessageCodec<T> implements MessageCodec<T, T> {
    private Class<T> clazz;

    public LocalMessageCodec(Class<T> clazz) {
      this.clazz = clazz;
    }

    @Override
    public void encodeToWire(Buffer buffer, T t) {
      throw new UnsupportedOperationException("Only local usage");
    }

    @Override
    public T decodeFromWire(int pos, Buffer buffer) {
      throw new UnsupportedOperationException("Only local usage");
    }

    @Override
    public T transform(T t) { //for immutable objects. return as is
      return t;
    }

    @Override
    public String name() {
      return "LocalMessageCodecFor" + clazz.getName();
    }

    @Override
    public byte systemCodecID() {
      return -1;
    }
  }
}
