package usecase.port.IDb;

import domain.entity.Ratable;
import domain.entity.RatingPoint;
import domain.entity.User;

import java.util.List;
import java.util.Optional;

public interface RatingDb {
    Ratable add(RatingPoint ratingToAdd);

    Optional<List<RatingPoint>> findByRatable(Ratable ratable);

    Optional<List<RatingPoint>> findByUser(User user);

    Optional<RatingPoint> findById(String ratingId);

    void update(RatingPoint point, int num);
}
