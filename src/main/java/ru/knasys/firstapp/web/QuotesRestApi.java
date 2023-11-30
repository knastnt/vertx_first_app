package ru.knasys.firstapp.web;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import lombok.extern.slf4j.Slf4j;
import ru.knasys.firstapp.web.dtos.Asset;
import ru.knasys.firstapp.web.dtos.Quote;

import java.math.BigDecimal;
import java.util.Random;

@Slf4j
public class QuotesRestApi {
  private static final Random random = new Random();
  public static void attach(Router parent) {
    parent.get("/quotes/:asset").handler(context -> {
      String asset = context.pathParam("asset");
      log.debug("Asset param = {}", asset);

      Quote quote = new Quote();
      quote.setAsset(new Asset(asset));
      quote.setBid(BigDecimal.valueOf(random.nextDouble()));
      quote.setAsk(quote.getBid().add(BigDecimal.ONE));
      quote.setLastPrice(quote.getAsk().add(quote.getBid()).divide(BigDecimal.valueOf(2)));
      quote.setVolume(BigDecimal.valueOf(random.nextDouble()));

      JsonObject response = JsonObject.mapFrom(quote);
      log.info("Path {} responds with {}", context.normalizedPath(), response.encodePrettily());
      context.response().putHeader("content-type", "application/json");
      context.response().end(response.toBuffer());
    })
    .failureHandler(RestFailureHandler.INSTANCE);
  }
}
