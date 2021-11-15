package com.boba.bobabuddy.infrastructure.assembler;

import com.boba.bobabuddy.core.entity.Store;
import com.boba.bobabuddy.infrastructure.controller.ItemController;
import com.boba.bobabuddy.infrastructure.controller.RatingController;
import com.boba.bobabuddy.infrastructure.controller.StoreController;
import com.boba.bobabuddy.infrastructure.dto.StoreDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StoreResourceAssembler extends SimpleIdentifiableRepresentationModelAssembler<StoreDto> {
    StoreResourceAssembler() {
        super(StoreController.class);
    }

    @Override
    public void addLinks(EntityModel<StoreDto> resource) {
        /**
         * Retain default links.
         */
        UUID id = Objects.requireNonNull(resource.getContent()).getId();
        resource.add(linkTo(methodOn(StoreController.class).findById(id)).withRel("self"));
        resource.add(linkTo(methodOn(StoreController.class).findAll()).withRel("stores"));


        // Add custom link to find all menu items
        resource.add(linkTo(methodOn(ItemController.class).findByStore(id)).withRel("items"));
        // Add custom link to find all ratings
        resource.add(linkTo(methodOn(RatingController.class).findByRatableObject("stores", id)).withRel("ratings"));
    }
}
