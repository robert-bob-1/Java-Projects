package Model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class BaseProduct extends MenuItem implements Serializable {
//    public String title;
//    public float rating, calories, proteins, fats, sodium, price;
    public BaseProduct(){

    }
    public BaseProduct(ArrayList<String> input){
        this.title = input.get(0);

        this.rating   = Float.parseFloat(input.get(1));
        this.calories = Float.parseFloat(input.get(2));
        this.proteins = Float.parseFloat(input.get(3));
        this.fats     = Float.parseFloat(input.get(4));
        this.sodium   = Float.parseFloat(input.get(5));
        this.price    = Float.parseFloat(input.get(6));
    }
    public BaseProduct(String string){
        ArrayList<String> input;
        String[] temp = string.split(",");
        input = new ArrayList<>(Arrays.asList(temp));
        this.title = input.get(0);

        this.rating   = Float.parseFloat(input.get(1));
        this.calories = Float.parseFloat(input.get(2));
        this.proteins = Float.parseFloat(input.get(3));
        this.fats     = Float.parseFloat(input.get(4));
        this.sodium   = Float.parseFloat(input.get(5));
        this.price    = Float.parseFloat(input.get(6));
    }

    @Override
    public float computeRating() {
        return rating;
    }

    @Override
    public float computeCalories() {
        return calories;
    }

    @Override
    public float computeProteins() {
        return proteins;
    }

    @Override
    public float computeFats() {
        return fats;
    }

    @Override
    public float computeSodium() {
        return sodium;
    }

    @Override
    public float computePrice() {
        return this.price;
    }

    @Override
    public String toString(){
        return title;
    }
    public static void main(String[] args) throws FileNotFoundException {
        FileReader fr = new FileReader("products.csv");
    }

}
