package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class CompositeProduct extends MenuItem implements Serializable{
//    public String title;
//    public float rating, calories, proteins, fats, sodium, price;
    public ArrayList<MenuItem> products;

    public CompositeProduct() {
        this.products = new ArrayList<>();
    }

    public CompositeProduct(String title, ArrayList<MenuItem> menuitems){
        this.title = title;
        this.products = menuitems;

        this.rating   = computeRating();
        this.calories = computeCalories();
        this.proteins = computeProteins();
        this.fats     = computeFats();
        this.sodium   = computeSodium();
        this.price    = computePrice();

        this.products = menuitems;
    }

    @Override
    public float computeRating() {
        float total = 0;
        int count = 0;
        for(MenuItem product : products){
            total += product.computeRating();
            count++;
        }
        return total/count;
    }

    @Override
    public float computeCalories() {
        float total = 0;
        for(MenuItem product : products)
            total += product.computeCalories();
        return total;
    }

    @Override
    public float computeProteins() {
        float total = 0;
        for(MenuItem product : products)
            total += product.computeProteins();
        return total;
    }

    @Override
    public float computeFats() {
        float total = 0;
        for(MenuItem product : products)
            total += product.computeFats();
        return total;
    }

    @Override
    public float computeSodium() {
        float total = 0;
        for(MenuItem product : products)
            total += product.computeSodium();
        return total;
    }

    @Override
    public float computePrice() {
        float totalPrice = 0;
        for(MenuItem product : products)
            totalPrice += product.computePrice();
        return totalPrice;
    }

    @Override
    public String toString(){ return title;}
}
