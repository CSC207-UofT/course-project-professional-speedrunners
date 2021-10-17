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

    public void run(){
        ISystemInputOutput user = new SystemInputOutput();
        String ifLoop = "y";
        String option;
        while(Objects.equals(ifLoop, "y")){
            user.output("Please select an operation:" +
                    "\n 1: createItem" +
                    "\n 2: findAll + Sort by price" +
                    "\n 3: RemoveItem");
            option = user.input();

            switch (option) {
                case "1":
                    CreateItemController createItemController = new CreateItemController(itemDb, idGen, presenter);
                    createItemController.createItem();
                    break;
                case "2":
                    FindItemController findItemsController = new FindItemController(itemDb, presenter);
                    findItemsController.findItems();
                    break;
                case "3":
                    DeleteItemController deleteItemController = new DeleteItemController(itemDb, idGen, presenter);
                    deleteItemController.deleteItem();
                    break;
            }
            user.output("Make another query? y/n");
            ifLoop = user.input();
        }





    }







}
