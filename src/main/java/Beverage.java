import java.util.HashMap;

public class Beverage {
  private String name;
  private HashMap<String,Integer> ingredients;

  public Beverage(String name, HashMap<String, Integer> ingredients) {
    this.name = name;
    this.ingredients = ingredients;
  }

  private void prepare(){
    System.out.println(name + "is prepared");
  }

  public HashMap<String, Integer> getIngredients() {
    return ingredients;
  }

  @Override
  public String toString() {
    return name + "\n" + ingredients.toString();
  }
}
