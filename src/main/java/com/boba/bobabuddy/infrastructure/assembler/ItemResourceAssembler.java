package com.boba.bobabuddy.infrastructure.assembler;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.infrastructure.controller.ItemController;
import com.boba.bobabuddy.infrastructure.controller.RatingPointController;
import com.boba.bobabuddy.infrastructure.controller.StoreController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ItemResourceAssembler extends SimpleIdentifiableRepresentationModelAssembler<Item> {
    ItemResourceAssembler() {
        super(ItemController.class);
    }

    @Override
    public void addLinks(EntityModel<Item> resource) {
        /**
         * Retain default links.
         */
        super.addLinks(resource);

        Item item = Objects.requireNonNull(resource.getContent());
        // Add custom link to find associated store
        resource.add(linkTo(methodOn(StoreController.class).findById(item.getStore().getId())).withRel("store"));
        // Add custom link to find all ratings
        resource.add(linkTo(methodOn(RatingPointController.class).findByRatableObject("item", item.getId())).withRel("ratings"));
    }
}
