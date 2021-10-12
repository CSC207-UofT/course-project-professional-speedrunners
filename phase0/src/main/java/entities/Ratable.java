package entities;

import java.util.List;

public interface Ratable {

    List<Rating> getRating();

    boolean deleteRating(Rating rating);

    boolean addRating(Rating rating);

}
