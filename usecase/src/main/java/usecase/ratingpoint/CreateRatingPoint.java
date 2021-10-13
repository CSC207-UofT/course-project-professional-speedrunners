package usecase.ratingpoint;

import domain.entity.Ratable;
import domain.entity.RatingPoint;
import usecase.port.IDb.RatingDb;


public final class CreateRatingPoint {

    private final RatingDb ratingDb;
    public CreateRatingPoint(final RatingDb ratingDb){
        this.ratingDb = ratingDb;
    }

    public Ratable add(RatingPoint point){
        RatingPoint ratingToAdd = RatingPoint.builder()
                .setRating(point.getRating())
                .setId(point.getId())
                .setRatable(point.getRatable())
                .setUser(point.getUser())
                .build();
        return ratingDb.add(ratingToAdd);
    }

}
