import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InputParser {

  public static CoffeeMachine getCoffeeMachine(String input){
    Gson gson = new Gson();
    JsonObject inputJson = gson.fromJson(input,JsonObject.class);
    Integer outlets = inputJson.get("machine").getAsJsonObject().get("outlets").getAsJsonObject().get("count_n").getAsInt();

    JsonObject itemsObject = inputJson.get("machine").getAsJsonObject().get("total_items_quantity").getAsJsonObject();
    ConcurrentHashMap<String,Integer> itemsHashMap = new ConcurrentHashMap<String, Integer>();
    for(Map.Entry<String, JsonElement> item: itemsObject.entrySet()){
      itemsHashMap.put(item.getKey(),item.getValue().getAsInt());
    }

    JsonObject beverages = inputJson.get("machine").getAsJsonObject().get("beverages").getAsJsonObject();
    HashMap<String,Beverage> beverageHashMap = new HashMap<String, Beverage>();
    for(Map.Entry<String, JsonElement> beverage: beverages.entrySet()){
      JsonObject beverageItemObject = beverages.get(beverage.getKey()).getAsJsonObject();
      HashMap<String,Integer> beverageItemsHashMap = new HashMap<String, Integer>();
      for(Map.Entry<String, JsonElement> beverageItem: beverageItemObject.entrySet()){
        beverageItemsHashMap.put(beverageItem.getKey(),beverageItem.getValue().getAsInt());
      }
      Beverage beverage1 = new Beverage(beverage.getKey(),beverageItemsHashMap);
      beverageHashMap.put(beverage.getKey(),beverage1);
    }
    CoffeeMachine coffeeMachine = new CoffeeMachine(outlets,itemsHashMap,beverageHashMap);
    return coffeeMachine;
  }
}
