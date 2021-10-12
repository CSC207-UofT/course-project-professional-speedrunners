package usecase.ratingpoint;

import domain.entity.RatingPoint;
import usecase.port.RatingDb;

public final class UpdateRatingPoint {
    private RatingDb ratingDb;

    public UpdateRatingPoint(final RatingDb ratingDb){
        this.ratingDb = ratingDb;
    }

    public void update(RatingPoint point, int num){
        ratingDb.update(point, num);

    }


}
