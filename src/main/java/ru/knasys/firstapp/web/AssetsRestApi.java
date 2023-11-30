package ru.knasys.firstapp.web;

import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import ru.knasys.firstapp.web.dtos.Asset;

@Slf4j
public class AssetsRestApi {
  public static void attach(Router parent) {
    parent.get("/assets").handler(context -> {
      JsonArray response = new JsonArray();
      response
        .add(new Asset("AAPL"))
        .add(new Asset("AMZN"))
        .add(new Asset("NFLX"))
        .add(new Asset("TSLA"));
      log.info("Path {} responds with {}", context.normalizedPath(), response.encodePrettily());
      context.response().end(response.toBuffer());
    })
    .failureHandler(RestFailureHandler.INSTANCE);
  }
}
