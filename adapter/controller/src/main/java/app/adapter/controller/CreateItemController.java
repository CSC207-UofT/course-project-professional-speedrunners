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
    private GenericController.ISystemInputOutput userInteractor;

    public CreateItemController(final ItemDb repo, final IdGenerator idGen, final PresenterInterface presenter,
                                GenericController.ISystemInputOutput userInteractor){
        this.repo = repo;
        this.idGen = idGen;
        this.presenter = presenter;
        this.userInteractor = userInteractor;
    }

    public void createItem(){
        float price;
        String storeId;
        String name;
        userInteractor.output("Enter the item Name:");
        name = userInteractor.input();
        userInteractor.output("Enter the item Price:");
        price = Float.parseFloat(userInteractor.input());
        userInteractor.output("Enter the storeId associated with the item:");
        storeId = userInteractor.input();
        ICreateItem createItem = new CreateItem(repo, idGen, presenter);
        createItem.create(createItem.generateRequest(price, storeId, name));
    }
}
