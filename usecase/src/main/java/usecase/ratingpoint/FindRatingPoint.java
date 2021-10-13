package usecase.ratingpoint;

import domain.entity.Ratable;
import domain.entity.RatingPoint;
import domain.entity.User;
import usecase.port.IDb.RatingDb;

import java.util.List;
import java.util.Optional;

public final class FindRatingPoint {

    private RatingDb ratingDb;

    public FindRatingPoint(final RatingDb ratingDb){
        this.ratingDb = ratingDb;
    }

    public Optional<RatingPoint> findById(final String ratingId){
        return ratingDb.findById(ratingId);
    }

    public Optional<List<RatingPoint>> fndByUser(final User user){
        return ratingDb.findByUser(user);
    }

    public Optional<List<RatingPoint>> findByRatable(final Ratable ratable){
        return ratingDb.findByRatable(ratable);
    }

}
