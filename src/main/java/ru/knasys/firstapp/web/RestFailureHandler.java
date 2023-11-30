package ru.knasys.firstapp.web;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum RestFailureHandler implements Handler<RoutingContext> {
  INSTANCE;
  private static final Logger LOG = LoggerFactory.getLogger(RestFailureHandler.class);
  @Override
  public void handle(RoutingContext context) {
    if (context.response().ended()) return;
    LOG.error("Router error", context.failure());
    context.response()
      .setStatusCode(500)
      .end(new JsonObject().put("error", context.failure().getMessage()).toBuffer());
  }
}
