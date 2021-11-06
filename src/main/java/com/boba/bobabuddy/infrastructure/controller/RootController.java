package com.boba.bobabuddy.infrastructure.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.boba.bobabuddy.core.entity.Item;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/api")
    public ResponseEntity<RepresentationModel> root(){
        RepresentationModel model = new RepresentationModel();

        model.add(linkTo(methodOn(RootController.class).root()).withSelfRel());
        model.add(linkTo(methodOn(ItemController.class).findAll()).withRel("items"));
        model.add(linkTo(methodOn(RatingPointController.class).findAll()).withRel("ratings"));
        model.add(linkTo(methodOn(StoreController.class).findAll()).withRel("stores"));
        model.add(linkTo(methodOn(UserController.class).findAll()).withRel("users"));

        return ResponseEntity.ok(model);
    }
}
