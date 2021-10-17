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

    public DeleteItemController(final ItemDb repo, final IdGenerator idGen, final PresenterInterface presenter){
        this.repo = repo;
        this.idGen = idGen;
        this.presenter = presenter;
    }

    public void deleteItem(){
        String itemId;
        ISystemInputOutput user = new SystemInputOutput();
        user.output("Enter the itemId associated with the item:");
        itemId = user.input();
        IRemoveItem removeItem = new RemoveItem(repo, presenter);
        removeItem.remove(removeItem.generateRequest(itemId));
    }
}
