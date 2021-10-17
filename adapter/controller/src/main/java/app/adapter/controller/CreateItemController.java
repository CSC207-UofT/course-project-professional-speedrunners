package app.adapter.controller;
import usecase.item.CreateItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IRequest.IItemIn.ICreateItem;
import usecase.port.IResponse.PresenterInterface;
import usecase.port.IdGenerator;


public class CreateItemController {
    private final ItemDb repo;
    private final IdGenerator idGen;
    private final PresenterInterface presenter;

    public CreateItemController(final ItemDb repo, final IdGenerator idGen, final PresenterInterface presenter){
        this.repo = repo;
        this.idGen = idGen;
        this.presenter = presenter;
    }

    public void createItem(){
        float price;
        String storeId;
        String name;
        ISystemInputOutput user = new SystemInputOutput();
        user.output("Enter the item Name:");
        name = user.input();
        user.output("Enter the item Price:");
        price = Float.parseFloat(user.input());
        user.output("Enter the storeId associated with the item:");
        storeId = user.input();
        ICreateItem createItem = new CreateItem(repo, idGen, presenter);
        createItem.create(createItem.generateRequest(price, storeId, name));
    }
}
