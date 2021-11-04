package com.boba.bobabuddy.infrastructure.controller.assembler;

import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.infrastructure.dto.ItemRepresentation;
import org.springframework.hateoas.server.RepresentationModelAssembler;

public class ItemRepresentationModelAssembler implements RepresentationModelAssembler<Item, ItemRepresentation> {
}
