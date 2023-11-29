package ru.knasys.firstapp.web;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.knasys.firstapp.web.dtos.Asset;

public class AssetsRestApi {
  private static final Logger LOG = LoggerFactory.getLogger(AssetsRestApi.class);
  public static void attach(Router parent) {
    parent.get("/assets").handler(context -> {
      JsonArray response = new JsonArray();
      response
        .add(new Asset("AAPL"))
        .add(new Asset("AMZN"))
        .add(new Asset("NFLX"))
        .add(new Asset("TSLA"));
      LOG.info("Path {} responds with {}", context.normalizedPath(), response.encodePrettily());
      context.response().end(response.toBuffer());
    })
    .failureHandler(context -> {
      if (context.response().ended()) return;
      LOG.error("Router error", context.failure());
      context.response()
        .setStatusCode(500)
        .end(new JsonObject().put("error", context.failure().getMessage()).toBuffer());
    });
  }
}
