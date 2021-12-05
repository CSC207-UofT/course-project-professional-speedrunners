package com.boba.bobabuddy.core.service.item.impl;

import com.boba.bobabuddy.core.data.dao.ItemJpaRepository;
import com.boba.bobabuddy.core.data.dto.ItemDto;
import com.boba.bobabuddy.core.domain.Item;
import com.boba.bobabuddy.core.exceptions.DifferentResourceException;
import com.boba.bobabuddy.core.service.firebaseImage.IImageService;
import com.boba.bobabuddy.core.service.item.FindItemService;
import com.boba.bobabuddy.core.service.item.UpdateItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.UUID;

/**
 * This class handle the usecase of updating items in the system.
 */
@Service("UpdateItemService")
@Transactional
public class UpdateItemServiceImpl implements UpdateItemService {
    private final ItemJpaRepository repo;
    private final FindItemService findItemService;
    private final IImageService imageService;

    /***
     * Construct the usecase class
     * @param repo the repository that hosts the Item entity.
     * @param findItemService Find item service to look for the item to update
     * @param imageService Uploads image and adds image url to resource
     */
    public UpdateItemServiceImpl(final ItemJpaRepository repo, FindItemService findItemService, IImageService imageService) {
        this.repo = repo;
        this.findItemService = findItemService;
        this.imageService = imageService;
    }


    @Override
    public Item updateItem(UUID itemId, ItemDto itemPatch) throws DifferentResourceException {
        Item itemToUpdate = findItemService.findById(itemId);

        itemToUpdate.setName(itemPatch.getName());
        itemToUpdate.setPrice(itemPatch.getPrice());

        return repo.save(itemToUpdate);
    }


    @Override
    public Item updateItemPrice(UUID itemId, float price) throws IllegalArgumentException{
        if (price < 0) {
            throw new IllegalArgumentException("Price less than 0.");
        }
        Item itemToUpdate = findItemService.findById(itemId);
        itemToUpdate.setPrice(price);
        return repo.save(itemToUpdate);
    }

    @Override
    public Item updateItemImage(UUID itemId, String imageUrl) throws IOException {
        Item itemToUpdate = findItemService.findById(itemId);

        String fileName = imageService.save(imageUrl, StringUtils.getFilename(imageUrl));
        String fbImageUrl = imageService.getImageUrl(fileName);

        itemToUpdate.setImageUrl(fbImageUrl);

        return repo.save(itemToUpdate);
    }
}
