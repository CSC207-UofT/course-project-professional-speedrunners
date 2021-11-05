package com.boba.bobabuddy.infrastructure.assembler;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.infrastructure.controller.ItemController;
import com.boba.bobabuddy.infrastructure.controller.RatingPointController;
import com.boba.bobabuddy.infrastructure.controller.StoreController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StoreResourceAssembler extends SimpleIdentifiableRepresentationModelAssembler<Store> {
    StoreResourceAssembler() {
        super(StoreController.class);
    }

    @Override
    public void addLinks(EntityModel<Store> resource) {
        /**
         * Retain default links.
         */
        super.addLinks(resource);

        UUID id = Objects.requireNonNull(resource.getContent()).getId();
        // Add custom link to find all menu items
        resource.add(linkTo(methodOn(ItemController.class).findByStore(id)).withRel("items"));
        // Add custom link to find all ratings
        resource.add(linkTo(methodOn(RatingPointController.class).findByRatableObject("store", id)).withRel("ratings"));
    }
}
