package com.boba.bobabuddy.core.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Abstract Class that defines a RatableObject in the domain layer
 * Item class and Store class extends this class
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public abstract class RatableObject {

    /***
     * avgRating is augmented in the class.
     * any mutation to the ratings field should also mutate avgRating accordingly
     */

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String imageUrl;

    @Min(value = 0)
    @Max(value = 1)
    private double avgRating;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "RATABLE_OBJECT_ID")
    @Builder.Default
    private Set<Rating> ratings = new HashSet<>();

    /***
     * note that this method computes the average rating and set both ratings field and avgRating field.
     */
    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
        if (ratings == null) return;

        float counter = 0;
        for (Rating i : ratings) {
            counter += i.getRating();
        }
        if (ratings.size() == 0) {
            setAvgRating(0);
        } else {
            setAvgRating(counter / ratings.size());
        }
    }

    /***
     * add a rating to the object and modify the avgRating accordingly
     * @param point a RatingPoint object
     * @return true if the operation was successful
     */
    public boolean addRating(Rating point) {
        if (ratings.contains(point)) {
            return false;
        }
        setAvgRating((avgRating * ratings.size() + point.getRating()) / (ratings.size() + 1));
        return ratings.add(point);

    }

    /***
     * remove a rating to the object and modify the avgRating accordingly
     * @param point a RatingPoint object
     * @return true if the operation was successful
     */
    public boolean removeRating(Rating point) {
        int size = ratings.size();
        boolean result = ratings.remove(point);
        if (result) {
            if (size - 1 == 0) {
                this.avgRating = 0;
            } else {
                this.avgRating = (float) (Math.round(avgRating * size) - point.getRating()) / (size - 1);
            }
        }
        return result;
    }

    /**
     * Update avgRating when a Rating is updated
     *
     * @param point     the updated Rating
     * @param oldRating the old Rating value
     * @param newRating the new Rating value
     * @return true if avgRating was updated
     */
    public boolean updateRating(Rating point, int oldRating, int newRating) {
        if (ratings.contains(point)) {
            int size = ratings.size();
            this.avgRating = (float) (Math.round(avgRating * size) - oldRating + newRating) / size;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RatableObject that = (RatableObject) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "avgRating = " + avgRating + ")";
    }
}