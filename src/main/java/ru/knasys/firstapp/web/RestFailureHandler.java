package ru.knasys.firstapp.web;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum RestFailureHandler implements Handler<RoutingContext> {
  INSTANCE;
  @Override
  public void handle(RoutingContext context) {
    if (context.response().ended()) return;
    log.error("Router error", context.failure());
    context.response()
      .setStatusCode(500)
      .end(new JsonObject().put("error", context.failure().getMessage()).toBuffer());
  }
}
