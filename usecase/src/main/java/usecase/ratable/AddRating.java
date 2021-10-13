package usecase.ratable;

import domain.entity.Ratable;
import domain.entity.RatingPoint;
import usecase.port.ItemDb;

public class AddRating {

    private ItemDb itemDb;

    public AddRating(final ItemDb itemDb){
        this.itemDb = itemDb;
    }

    public boolean add(final RatingPoint rating, final Ratable ratable){
        itemDb.
    }


}
