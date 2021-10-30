package com.boba.bobabuddy.core.usecase.item;


import com.boba.bobabuddy.core.entity.Item;
import com.boba.bobabuddy.core.usecase.port.IResponse.PresenterInterface;
import com.boba.bobabuddy.core.usecase.port.itemport.ICreateItem;
import com.boba.bobabuddy.infrastructure.database.ItemJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class handle the usecase of creating item and adding it into the system.
 * It implements the ICreateItem interface which defines what operations are supported by this usecase object
 * from a controller's perspective.
 */

// Springframework annotations that marks this class as a service component
// Functionally identical to the @Component annotation as far as I'm aware, and it essentially
// registers the class as a component (bean) so that Spring can automatically configure and inject dependencies
// as needed.
@Service
// Indicates that operations performed in this class in Transactional.
// Refers to this link for more info: https://java.christmas/2019/24
@Transactional
public class CreateItem implements ICreateItem {
    /***
     * JPA repository port, probably the most important part of the code
     * Handles queries and update, creation, deletion of entries in the database
     */
    private final ItemJpaRepository repo;
    // Unused and will possibly be depreciated.
    private final PresenterInterface presenter; // port for injecting presenter implementations.

    /**
     * Initialize the Create Item usecase by injecting it with required dependencies.
     *
     * @param repo      a database object for handling item data
     * @param presenter a presenter object responsible for translating data from usecase readable format into view
     *                  object readable format (view model).
     *                  note this is currently handled automatically via Spring and a hand coded presenter may not be
     *                  necessary
     */
    // Spring annotation that instruct springboot to attempt to automatically inject dependencies as needed.
    @Autowired
    public CreateItem(ItemJpaRepository repo, PresenterInterface presenter) {
        this.repo = repo;
        this.presenter = presenter;
    }


    /***
     * Create an item and save it to the database
     * Note that the actual building of an Item object is handled by the CreateItemRequest
     * wrapper which help with converting JSON request into Entity objects via HTTPMessageConverter.
     * TODO: Need Testing
     *     JpaRepository.save not only add new entity into the database, if the entity passed to the
     *     method already exists, it will also overwrite the existing entity with new data.
     *     this method should not be allowed to do this since it will be called with a HTTP POST
     *     request instead of PUT or PATCH.
     *     This should not happen since the controller constructs a CreateItemRequest object, which constructs
     *     a Item object with random UUID. But testing will be required.
     * @param item Item to be persisted in the database.
     * @return the Item object that was persisted in the database.
     */
    @Override
    public Item create(Item item) {
        return repo.save(item);
    }
}
