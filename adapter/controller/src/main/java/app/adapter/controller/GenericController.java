package app.adapter.controller;

import app.adapter.presenter.GenericPresenter;
import app.adapter.id_generator.uuid.UuidGen;
import app.adapter.db.item_db.ItemDbHashmap;
import usecase.port.IDb.ItemDb;
import usecase.port.IResponse.PresenterInterface;
import usecase.port.IdGenerator;
import java.util.Objects;

public class GenericController  {

    private final ItemDb itemDb;
    private final IdGenerator idGen;
    private final PresenterInterface presenter;

    public GenericController(){
        this.itemDb = new ItemDbHashmap();
        this.idGen = new UuidGen();
        this.presenter = new GenericPresenter();
    }

    public interface ISystemInputOutput {
        void output(String message);
        String input();
    }

    public void run(ISystemInputOutput userInteractor){
        String ifLoop = "y";
        String option;
        while(Objects.equals(ifLoop, "y")){
            userInteractor.output("Please select an operation:" +
                    "\n 1: createItem" +
                    "\n 2: findAll + Sort by price" +
                    "\n 3: RemoveItem");
            option = userInteractor.input();

            switch (option) {
                case "1":
                    CreateItemController createItemController = new CreateItemController(itemDb, idGen, presenter,
                            userInteractor);
                    createItemController.createItem();
                    break;
                case "2":
                    FindItemController findItemsController = new FindItemController(itemDb, presenter);
                    findItemsController.findItems();
                    break;
                case "3":
                    DeleteItemController deleteItemController = new DeleteItemController(itemDb, idGen, presenter,
                            userInteractor);
                    deleteItemController.deleteItem();
                    break;
            }
            userInteractor.output("Make another query? y/n");
            ifLoop = userInteractor.input();
        }
    }







}
