package app.adapter.controller;

import app.adapter.controller.port.IViewFindItem;
import usecase.item.FindItem;
import usecase.item.RemoveItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IRequest.IItemIn.IFindItem;
import usecase.port.IResponse.PresenterInterface;

public class FindItemController {
    //private final IViewFindItem view;
    private final IFindItem findItem;
    public FindItemController(final ItemDb repo, final PresenterInterface presenter){
        this.findItem = new FindItem(repo, presenter);
    }

    public void findItems(){
        findItem.findAll(findItem.requestFindAll("p"));
    }
}
