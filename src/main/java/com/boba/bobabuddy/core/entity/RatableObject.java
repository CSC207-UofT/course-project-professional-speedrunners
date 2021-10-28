package com.boba.bobabuddy.core.entity;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class RatableObject {
    private @Id
    @GeneratedValue
    UUID id;
    private @OneToMany(cascade = CascadeType.ALL)
    Set<RatingPoint> ratings;
    float avgRating;

    public RatableObject(Set<RatingPoint> ratings) {
        this.ratings = ratings;
    }

    protected RatableObject() {

    }

    public Set<RatingPoint> getRatings() {
        return ratings;
    }

    public void setRatings(Set<RatingPoint> ratings) {
        this.ratings = ratings;
        float counter = 0;
        for (RatingPoint i : ratings) {
            counter += i.getRating();
        }
        this.avgRating = counter / ratings.size();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public float getAvgRating() {
        return avgRating;
    }

    //TODO: Check if this formula is correct
    public boolean addRating(RatingPoint point) {
        this.avgRating = (avgRating * ratings.size() + point.getRating()) / (ratings.size() + 1);
        return ratings.add(point);

    }

    //TODO: Check if this formula is correct
    public boolean removeRating(RatingPoint point) {
        int size = ratings.size();
        boolean result = ratings.remove(point);
        if(result){
            this.avgRating = (avgRating*size - point.getRating())/(size - 1);
        }
        return result;
    }


}