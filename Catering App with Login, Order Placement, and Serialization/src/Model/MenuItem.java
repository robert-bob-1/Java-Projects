package Model;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    public String title;
    public float rating, calories, proteins, fats, sodium, price;
    public int timesOrdered = 0;

    public abstract float computeRating();
    public abstract float computeCalories();
    public abstract float computeProteins();
    public abstract float computeFats();
    public abstract float computeSodium();
    public abstract float computePrice();
    public abstract String toString();
}
