package ru.knasys.firstapp.web;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetsRestApi {
  private static final Logger LOG = LoggerFactory.getLogger(AssetsRestApi.class);
  public static void attach(Router parent) {
    parent.get("/assets").handler(context -> {
      JsonArray response = new JsonArray();
      response
        .add(new JsonObject().put("symbol", "AAPL"))
        .add(new JsonObject().put("symbol", "AMZN"))
        .add(new JsonObject().put("symbol", "NFLX"))
        .add(new JsonObject().put("symbol", "TSLA"));
      LOG.info("Path {} responds with {}", context.normalizedPath(), response.encodePrettily());
      context.response().end(response.toBuffer());
    });
  }
}
