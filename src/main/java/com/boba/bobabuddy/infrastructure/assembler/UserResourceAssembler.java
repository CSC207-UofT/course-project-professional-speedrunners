package com.boba.bobabuddy.infrastructure.assembler;

import com.boba.bobabuddy.core.entity.User;
import com.boba.bobabuddy.infrastructure.controller.RatingController;
import com.boba.bobabuddy.infrastructure.controller.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserResourceAssembler extends SimpleIdentifiableRepresentationModelAssembler<User> {

    UserResourceAssembler() {
        super(UserController.class);
    }

    @Override
    public void addLinks(EntityModel<User> resource) {
        /**
         * Retain default links.
         */
        String email = Objects.requireNonNull(resource.getContent()).getEmail();

        resource.add(linkTo(methodOn(UserController.class).findByEmail(email)).withRel("self"));
        resource.add(linkTo(methodOn(UserController.class).findAll()).withRel("users"));

        // Add custom link to find all ratings
        resource.add(linkTo(methodOn(RatingController.class).findByUser(email)).withRel("ratings"));

    }
}
