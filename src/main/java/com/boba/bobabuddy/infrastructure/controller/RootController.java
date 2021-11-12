package com.boba.bobabuddy.infrastructure.controller;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RootController {

    @GetMapping()
    public ResponseEntity<RepresentationModel<?>> root() {
        var model = new RepresentationModel<>();

        model.add(linkTo(methodOn(RootController.class).root()).withSelfRel());
        model.add(linkTo(methodOn(ItemController.class).findAll()).withRel("items"));
        model.add(linkTo(methodOn(RatingController.class).findAll()).withRel("ratings"));
        model.add(linkTo(methodOn(StoreController.class).findAll()).withRel("stores"));
        model.add(linkTo(methodOn(UserController.class).findAll()).withRel("users"));

        return ResponseEntity.ok(model);
    }
}
