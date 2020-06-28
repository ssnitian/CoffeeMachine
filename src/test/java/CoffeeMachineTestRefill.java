public class CoffeeMachineTestRefill {
  public static void main(String[] args) throws InterruptedException {
    String input = "{\n" +
                           "  \"machine\": {\n" +
                           "    \"outlets\": {\n" +
                           "      \"count_n\": 4\n" +
                           "    },\n" +
                           "    \"total_items_quantity\": {\n" +
                           "      \"hot_water\": 1000,\n" +
                           "      \"hot_milk\": 1500,\n" +
                           "      \"ginger_syrup\": 100,\n" +
                           "      \"sugar_syrup\": 100,\n" +
                           "      \"tea_leaves_syrup\": 100\n" +
                           "    },\n" +
                           "    \"beverages\": {\n" +
                           "      \"hot_tea\": {\n" +
                           "        \"hot_water\": 100,\n" +
                           "        \"hot_milk\": 50,\n" +
                           "        \"ginger_syrup\": 10,\n" +
                           "        \"sugar_syrup\": 10,\n" +
                           "        \"tea_leaves_syrup\": 30\n" +
                           "      },\n" +
                           "      \"hot_coffee\": {\n" +
                           "        \"hot_water\": 100,\n" +
                           "        \"ginger_syrup\": 30,\n" +
                           "        \"hot_milk\": 400,\n" +
                           "        \"sugar_syrup\": 50,\n" +
                           "        \"tea_leaves_syrup\": 30\n" +
                           "      },\n" +
                           "      \"black_tea\": {\n" +
                           "        \"hot_water\": 300,\n" +
                           "        \"ginger_syrup\": 30,\n" +
                           "        \"sugar_syrup\": 50,\n" +
                           "        \"tea_leaves_syrup\": 30\n" +
                           "      },\n" +
                           "      \"green_tea\": {\n" +
                           "        \"hot_water\": 100,\n" +
                           "        \"ginger_syrup\": 30,\n" +
                           "        \"sugar_syrup\": 50\n" +
                           "      }\n" +
                           "    }\n" +
                           "  }\n" +
                           "}";

    CoffeeMachine coffeeMachine = InputParser.getCoffeeMachine(input);
    coffeeMachine.prepare("hot_tea");
    coffeeMachine.prepare("black_tea");
    coffeeMachine.prepare("green_tea");
    coffeeMachine.prepare("hot_tea");
    coffeeMachine.refill("sugar_syrup",100);
    coffeeMachine.prepare("hot_tea");
  }
}
