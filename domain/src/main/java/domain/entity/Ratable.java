package domain.entity;

import java.util.List;

public interface Ratable {

    List<RatingPoint> getRatings();

    float getAvgRating();

    boolean addRating(RatingPoint point);

    boolean removeRating(RatingPoint point);


}