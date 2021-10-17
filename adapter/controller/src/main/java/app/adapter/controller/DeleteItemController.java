package app.adapter.controller;

import app.adapter.controller.port.IViewCreateItem;
import app.adapter.controller.port.IViewRemoveItem;
import usecase.item.CreateItem;
import usecase.item.RemoveItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IRequest.IItemIn.ICreateItem;
import usecase.port.IRequest.IItemIn.IRemoveItem;
import usecase.port.IResponse.PresenterInterface;
import usecase.port.IdGenerator;

public class DeleteItemController {
    private final IViewRemoveItem view;
    private final IRemoveItem removeItem;



    public DeleteItemController(final ItemDb repo, final PresenterInterface presenter, IViewRemoveItem view){
        this.view = view;
        this.removeItem = new RemoveItem(repo, presenter);
    }

    public void deleteItem(){
        removeItem.remove(removeItem.generateRequest(view.getItemId()));
    }
}
