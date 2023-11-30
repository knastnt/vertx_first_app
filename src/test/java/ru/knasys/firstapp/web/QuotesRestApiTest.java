package ru.knasys.firstapp.web;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.knasys.firstapp.MainVerticle;

import static org.junit.jupiter.api.Assertions.*;
import static ru.knasys.firstapp.MainVerticle.PORT;

@Slf4j
@ExtendWith(VertxExtension.class)
class QuotesRestApiTest {

  @BeforeEach
  void init(Vertx vertx, VertxTestContext context) {
    vertx.deployVerticle(new MainVerticle(), context.succeedingThenComplete());
  }

  @Test
  void test(Vertx vertx, VertxTestContext context) {
    WebClient webClient = WebClient.create(vertx, new WebClientOptions().setDefaultPort(PORT));
    webClient
      .get("/quotes/TSLA")
      .send()
      .onComplete(context.succeeding(response -> {
        JsonObject json = response.bodyAsJsonObject();
        log.info("Response: {}", json);
        assertEquals("TSLA", json.getJsonObject("asset").getString("symbol"));
        assertNotNull(json.getDouble("bid"));
        assertNotNull(json.getDouble("ask"));
        assertNotNull(json.getDouble("lastPrice"));
        assertNotNull(json.getDouble("volume"));
        assertEquals(200, response.statusCode());
        context.completeNow();
      }));
  }
}
