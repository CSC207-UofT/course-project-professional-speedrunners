package entities;

import java.util.ArrayList;
import java.util.List;

public class BubbleTea extends Item implements Ratable{
    ArrayList<Rating> ratings;


    public BubbleTea(float price, Store store, String id) {
        super(price, store, id);
        this.ratings = new ArrayList<Rating>();
    }

    @Override
    public List<Rating> getRating() {
        return ratings;
    }

    @Override
    public boolean deleteRating(Rating rating) {
        //TODO
        return false;
    }

    @Override
    public boolean addRating(Rating rating) {
        //TODO
        return false;
    }
}
