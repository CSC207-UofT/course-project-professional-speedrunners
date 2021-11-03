package com.boba.bobabuddy.core.entity;

import org.hibernate.Hibernate;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Abstract Class that defines a RatableObject in the domain layer
 * This replaces the Ratable interface
 * Item class and Store class extends this class
 * Note that entities are now coupled directly with Persistence implementation (JPA)
 * This is done to save time and reduce boilerplate codes.
 */

// JPA annotation indicating that the class is an entity to be persisted.
@Entity
// JPA annotation indicating that this class has child entities that also need to be persisted.
// Table per class strategy separates Item and Store into two separate tables in the SQL database.
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class RatableObject extends RepresentationModel<RatableObject> {

    /***
     * avgRating is augmented in the class.
     * any mutation to the ratings field should also mutate avgRating accordingly
     */
    float avgRating;
    /***
     * JPA annotation indicating that the field is the primary key for the entity.
     * GeneratedValue annotation instructs JPA to auto generate a primary key value.
     */
    private @Id
    @GeneratedValue
    UUID id;
    private String name;
    /***
     * JPA annotation to indicate one-to-many relationship between RatableObject and RatingPoint.
     * cascade parameter tells JPA that if RatableObject's ratings field is mutated, those changes to the RatingPoint
     * entities should also be persisted
     */
    private @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
            @JoinColumn(name = "rating_id")
    Set<RatingPoint> ratings;

    /***
     * Constructor that initiates RatableObject
     * Note that a no parameter constructor is also required by JPA
     */
    public RatableObject(String name) {
        this.name = name;
        this.ratings = new HashSet<>();
        this.avgRating = 0;
    }

    protected RatableObject() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setters
    public Set<RatingPoint> getRatings() {
        return ratings;
    }

    /***
     * note that this method computes the average rating and set both ratings field and avgRating field.
     */
    public void setRatings(Set<RatingPoint> ratings) {
        this.ratings = ratings;
        float counter = 0;
        for (RatingPoint i : ratings) {
            counter += i.getRating();
        }
        setAvgRating(counter / ratings.size());
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

    // Should not be called from outside the class
    private void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    //TODO: Check if this formula is correct. need to be tested
    public boolean addRating(RatingPoint point) {
        this.avgRating = (avgRating * ratings.size() + point.getRating()) / (ratings.size() + 1);
        return ratings.add(point);

    }

    //TODO: Check if this formula is correct. need to be tested
    public boolean removeRating(RatingPoint point) {
        int size = ratings.size();
        boolean result = ratings.remove(point);
        if (result) {
            this.avgRating = (avgRating * size - point.getRating()) / (size - 1);
        }
        return result;
    }

    // It is sufficient to determine equality by comparing the primary key (UUID) alone.
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
                "avgRating = " + avgRating + ")";
    }
}