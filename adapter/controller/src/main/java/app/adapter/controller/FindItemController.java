package app.adapter.controller;

import usecase.item.FindItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IRequest.IItemIn.IFindItem;
import usecase.port.IResponse.PresenterInterface;

public class FindItemController {
    private final ItemDb repo;
    private final PresenterInterface presenter;

    public FindItemController(final ItemDb repo, final PresenterInterface presenter){
        this.repo = repo;
        this.presenter = presenter;
    }

    public void findItems(){
        IFindItem findItem = new FindItem(repo, presenter);
        findItem.findAll(findItem.requestFindAll("p"));
    }
}
