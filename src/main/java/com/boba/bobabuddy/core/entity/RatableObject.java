package com.boba.bobabuddy.core.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.Hibernate;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class RatableObject extends RepresentationModel<RatableObject> {

    /***
     * avgRating is augmented in the class.
     * any mutation to the ratings field should also mutate avgRating accordingly
     */
    private float avgRating;
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
    @JoinColumn(name = "ratable_object_id")
    @JsonIdentityReference(alwaysAsId = true)
    Set<Rating> ratings;

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

    public Set<Rating> getRatings() {
        return ratings;
    }

    /***
     * note that this method computes the average rating and set both ratings field and avgRating field.
     */
    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
        float counter = 0;
        for (Rating i : ratings) {
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
    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    //TODO: Check if this formula is correct. need to be tested

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

    //TODO: Check if this formula is correct. need to be tested

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
                this.avgRating = (avgRating * size - point.getRating()) / (size - 1);
            }
        }
        return result;
    }

    /**
     * Update avgRating when a Rating is updated
     * @param point the updated Rating
     * @param oldRating the old Rating value
     * @param newRating the new Rating value
     * @return true if avgRating was updated
     */
    public boolean updateRating(Rating point, int oldRating, int newRating){
        if (ratings.contains(point)){
            int size = ratings.size();
            this.avgRating = (avgRating * size - oldRating + newRating) / size;
            return true;
        }
        return false;
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
                "id = " + getId() + ", " +
                "avgRating = " + getAvgRating() + ", " +
                "name = " + getName() + ", " +
                "links = " + getLinks() + ")";
    }
}