package com.boba.bobabuddy.infrastructure.assembler;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.entity.Rating;
import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.infrastructure.controller.ItemController;
import com.boba.bobabuddy.infrastructure.controller.RatingController;
import com.boba.bobabuddy.infrastructure.controller.StoreController;
import com.boba.bobabuddy.infrastructure.controller.UserController;
import com.boba.bobabuddy.infrastructure.dto.RatingDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RatingResourceAssembler extends SimpleIdentifiableRepresentationModelAssembler<RatingDto> {
    RatingResourceAssembler() {
        super(RatingController.class);
    }

    @Override
    public void addLinks(EntityModel<RatingDto> resource){
        /**
         * Retain default links.
         */
        RatingDto rating = Objects.requireNonNull(resource.getContent());
        resource.add(linkTo(methodOn(RatingController.class).findById(rating.getId())).withRel("self"));

        // Add custom link to find associated user
        resource.add(linkTo(methodOn(UserController.class).findByEmail(rating.getUserDto().getEmail())).withRel("users"));

        // Add custom link to find associated ratable object
        if (rating.getRatableObject() instanceof Item) {
            resource.add(linkTo(methodOn(ItemController.class).findById(rating.getRatableObject().getId())).withRel("items"));
        } else if (rating.getRatableObject() instanceof Store) {
            resource.add(linkTo(methodOn(StoreController.class).findById(rating.getRatableObject().getId())).withRel("stores"));
        }
    }
}
