package app.adapter.controller;
import app.adapter.controller.port.IViewCreateItem;
import usecase.item.CreateItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IRequest.IItemIn.ICreateItem;
import usecase.port.IResponse.PresenterInterface;
import usecase.port.IdGenerator;

public class CreateItemController {

    private final IViewCreateItem view;
    private final ICreateItem createItem;

    public CreateItemController(final ItemDb repo, final IdGenerator idGen, final PresenterInterface presenter, IViewCreateItem view){
        this.view = view;
        this.createItem = new CreateItem(repo, idGen, presenter);
    }

    public void createItem(){
        float price;
        String storeId;
        String name;

        name = view.getItemName();
        price = view.getItemPrice();
        storeId = view.getStoreId();
        createItem.create(createItem.generateRequest(price, storeId, name));
    }
}
