package ru.knasys.firstapp.web;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.knasys.firstapp.MainVerticle;

import static org.junit.jupiter.api.Assertions.*;
import static ru.knasys.firstapp.MainVerticle.PORT;

@ExtendWith(VertxExtension.class)
class AssetsRestApiTest {
  private static final Logger LOG = LoggerFactory.getLogger(AssetsRestApiTest.class);

  @BeforeEach
  void init(Vertx vertx, VertxTestContext context) {
    /*
    Handler<AsyncResult<String>> asyncResultHandler = stringAsyncResult -> {
      if (stringAsyncResult.succeeded()) context.completeNow();
      if (stringAsyncResult.failed()) context.failNow(stringAsyncResult.cause());
    };
    vertx.deployVerticle(new MainVerticle(), asyncResultHandler);
     */

    // Конструкцию выше можно заменить на:
    //vertx.deployVerticle(new MainVerticle(), context.succeeding(id -> context.completeNow()));

    //Или на:
    vertx.deployVerticle(new MainVerticle(), context.succeedingThenComplete());
  }

  @Test
  void test(Vertx vertx, VertxTestContext context) {
    //В чём разница между клиентами?

    /*
    HttpClientOptions httpClientOptions = new HttpClientOptions();
    httpClientOptions.setDefaultPort(PORT);
    HttpClient httpClient = vertx.createHttpClient(httpClientOptions);
    httpClient.request(HttpMethod.GET, "/assets"). х.3
     */

    WebClientOptions webClientOptions = new WebClientOptions(); // extends HttpClientOptions
    webClientOptions.setDefaultPort(PORT);
    WebClient webClient = WebClient.create(vertx, webClientOptions);
    Future<HttpResponse<Buffer>> responseFuture = webClient.get("/assets").send();
    responseFuture.onComplete(context.succeeding(response -> {
      JsonArray json = response.bodyAsJsonArray();
      LOG.info("Response: {}", json);
      assertEquals("[{\"symbol\":\"AAPL\"},{\"symbol\":\"AMZN\"},{\"symbol\":\"NFLX\"},{\"symbol\":\"TSLA\"}]", json.encode());
      assertEquals(200, response.statusCode());
      context.completeNow();
    }));
//    responseFuture.onComplete(httpResponseAsyncResult -> {
//      httpResponseAsyncResult.
//    });
  }
}
