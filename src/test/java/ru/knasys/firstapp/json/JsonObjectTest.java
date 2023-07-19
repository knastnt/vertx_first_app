package ru.knasys.firstapp.json;

import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class JsonObjectTest {
  @Test
  void jsonObjectCanBeMapped() {
    JsonObject myJsonObject = new JsonObject();
    myJsonObject.put("id", 1);
    myJsonObject.put("name", "Kot");
    myJsonObject.put("loves_vertex", true);

    String encode = myJsonObject.encode();
    assertEquals("{\"id\":1,\"name\":\"Kot\",\"loves_vertex\":true}", encode);

    JsonObject decodedJsonObject = new JsonObject(encode);
    assertEquals(myJsonObject, decodedJsonObject);
  }

  @Test
  void jsonObjectCanBeCreatedFromMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("id", 1);
    map.put("name", "Kot");
    map.put("loves_vertex", true);

    JsonObject jsonObject = new JsonObject(map);
    assertSame(map, jsonObject.getMap());
  }


}
