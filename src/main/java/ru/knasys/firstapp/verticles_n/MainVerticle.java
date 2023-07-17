package ru.knasys.firstapp.verticles_n;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import ru.knasys.firstapp.verticles.VerticleA;
import ru.knasys.firstapp.verticles.VerticleB;

import java.util.UUID;

public class MainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Vertx v = Vertx.vertx();

    v.deployVerticle(new MainVerticle())
      .onComplete(event ->
        System.out.println("init completed"));
  }
  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    Future<String> future = vertx.deployVerticle(
      VerticleN.class.getName(),
      new DeploymentOptions()
        .setInstances(4)
        .setConfig(new JsonObject()
          .put("id", UUID.randomUUID().toString())
          .put("name", VerticleN.class.getName())
        )
    );

    System.out.println("MainVerticle started");
    startPromise.complete();
  }
}
