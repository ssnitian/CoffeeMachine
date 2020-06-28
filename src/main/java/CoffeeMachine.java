import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class CoffeeMachine{
  private int outlets;
  private ConcurrentHashMap<String,Integer> items;
  private HashMap<String,Beverage> beverages;
  private final ExecutorService executorService;


  /*
  * Executore Service is for N outlets
  */
  public CoffeeMachine(int outlets,ConcurrentHashMap<String,Integer> items,HashMap<String,Beverage> beverages) {
    this.outlets = outlets;
    this.beverages = beverages;
    this.items =items;
    this.executorService = Executors.newFixedThreadPool(this.outlets, new ThreadFactory() {
      int counter = 0;
      @Override
      public Thread newThread(Runnable r) {
        ++counter;
        return new Thread(r, "counter " + counter + " running");
      }
    });
    //printIngredients();
  }

  public void refill(String item,Integer val) throws InterruptedException {
    executorService.awaitTermination(10, TimeUnit.SECONDS);
    System.out.println("Refilling " + item + "....");
    if(items.containsKey(item)){
      items.put(item,items.get(item).intValue()+val);
    }else {
      items.put(item,val);
    }
  }


  /*
  * This method checks if the ingredients for making beverages are available or not
  */
  public ArrayList<String> checkNotAvailability(String beverage){
    ArrayList<String> itemsNotPresent = new ArrayList<String>();
    HashMap<String,Integer> ingredients = beverages.get(beverage).getIngredients();
    for(Map.Entry<String, Integer> ingredient: ingredients.entrySet()){
      if(!items.containsKey(ingredient.getKey())){
        itemsNotPresent.add(ingredient.getKey());
      }
    }
    return itemsNotPresent;
  }

  public void printIngredients(){
    System.out.println("Items : " + items.toString());
  }

  /*
  * This method check if ingredients are sufficient and reduces the ingredients if sufficient
  * This method is synchronized because it needs accesses the shared resource items.
  */
  public synchronized ArrayList<String> checkNReduceIngredients(String beverage){
    ArrayList<String> itemsNotSufficient = new ArrayList<String>();
    HashMap<String,Integer> ingredients = beverages.get(beverage).getIngredients();
    for(Map.Entry<String, Integer> ingredient: ingredients.entrySet()){
      if(ingredient.getValue()>items.get(ingredient.getKey())){
        itemsNotSufficient.add(ingredient.getKey());
        System.out.println(ingredient.getKey() + " is running low");
      }
    }
    if(itemsNotSufficient.size()==0)
    reduceIngredients(beverage);
    return itemsNotSufficient;
  }


  /*
  * This method reduces the ingredients if sufficient
  */
  public void reduceIngredients(String beverage){
    HashMap<String,Integer> ingredients = beverages.get(beverage).getIngredients();
    for(Map.Entry<String, Integer> ingredient: ingredients.entrySet()){
      items.put(ingredient.getKey(),items.get(ingredient.getKey())-ingredient.getValue());
    }
  }

  /*
  * This method checks for necessary condition and prepares the beverage
  */
  public void prepare(final String beverage){
    executorService.submit(new Runnable() {
      @Override
      public void run() {
        if(!beverages.containsKey(beverage)){
          System.out.println(beverage + " is not available");
        }
        ArrayList<String> notAvailableItems = checkNotAvailability(beverage);
        if(notAvailableItems.size()>0){
          String result = beverage + " cannot be prepared because ";
          for(String s: notAvailableItems){
            result = result + s + ", ";
          }
          result = result.substring(0,result.length()-2);
          result = result + " is not available";
          System.out.println(result);
        }else{
          ArrayList<String> itemsNotSufficient = checkNReduceIngredients(beverage);
          if(itemsNotSufficient.size()>0){
            String result = beverage + " cannot be prepared because ";
            for(String s: itemsNotSufficient){
              result = result + s + ", ";
            }
            result = result.substring(0,result.length()-2);
            result = result + " is not sufficient";
            System.out.println(result);
          }else{
            System.out.println(beverage + " is prepared");
          }
        }
      }
    });
  }
}
