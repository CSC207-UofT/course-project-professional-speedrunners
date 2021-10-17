package app.adapter.controller;

import usecase.item.RemoveItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IRequest.IItemIn.IRemoveItem;
import usecase.port.IResponse.PresenterInterface;
import usecase.port.IdGenerator;

public class DeleteItemController {
    private final ItemDb repo;
    private final IdGenerator idGen;
    private final PresenterInterface presenter;
    private GenericController.ISystemInputOutput userInteractor;


    public DeleteItemController(final ItemDb repo, final IdGenerator idGen, final PresenterInterface presenter,
                                GenericController.ISystemInputOutput userInteractor){
        this.repo = repo;
        this.idGen = idGen;
        this.presenter = presenter;
        this.userInteractor = userInteractor;
    }

    public void deleteItem(){
        String itemId;
        userInteractor.output("Enter the itemId associated with the item:");
        itemId = userInteractor.input();
        IRemoveItem removeItem = new RemoveItem(repo, presenter);
        removeItem.remove(removeItem.generateRequest(itemId));
    }
}
