package domain.entity;

import java.util.ArrayList;
import java.util.List;

public class RatableItem extends Item implements Ratable{

    List<RatingPoint> lst;

    public RatableItem(float price, Store store, String id) {
        super(price, store, id);
        this.lst = new ArrayList<>();
    }

    @Override
    public List<RatingPoint> getRatings() {
        return lst;
    }

    @Override
    public float getAvgRating() {
        float counter = 0;

        for (RatingPoint i: lst){
            counter += i.getRating();
        }

        return counter/lst.size();
    }

    //TODO: handle duplicate situation (same user same ratable)
    @Override
    public boolean addRating(RatingPoint point) {
       lst.add(point);
       return true;
    }

    @Override
    public boolean removeRating(RatingPoint point) {
        return lst.remove(point);
    }
    

}
