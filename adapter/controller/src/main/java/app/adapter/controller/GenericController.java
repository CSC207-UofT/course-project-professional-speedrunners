package app.adapter.controller;

import app.adapter.controller.port.IView;
import usecase.port.IDb.ItemDb;
import usecase.port.IResponse.PresenterInterface;
import usecase.port.IdGenerator;

public class GenericController{

    private final ItemDb itemDb;
    private final IdGenerator idGen;
    private final PresenterInterface presenter;
    private final IView view;


    public GenericController(ItemDb itemDb, IdGenerator idGen, PresenterInterface presenter, IView view){
        this.itemDb = itemDb;
        this.idGen = idGen;
        this.presenter = presenter;
        this.view = view;

    }

    public void run(){
        String operation;

        do{
            operation = view.getOperation();
            switch (operation) {
                case "1":
                    CreateItemController createItemController = new CreateItemController(itemDb, idGen, presenter,
                            view);
                    createItemController.createItem();
                    break;
                case "2":
                    FindItemController findItemsController = new FindItemController(itemDb, presenter);
                    findItemsController.findItems();
                    break;
                case "3":
                    DeleteItemController deleteItemController = new DeleteItemController(itemDb, presenter,
                            view);
                    deleteItemController.deleteItem();
                    break;
            }
        }while(view.getAnotherQuery());
    }

    public static GenericControllerBuilder builder(){return new GenericControllerBuilder();}

    public static class GenericControllerBuilder{
        private ItemDb itemDb;
        private IdGenerator idGen;
        private PresenterInterface presenter;
        private IView view;

        public GenericControllerBuilder setItemDb(ItemDb itemDb){
            this.itemDb = itemDb;
            return this;
        }

        public GenericControllerBuilder setIdGen(IdGenerator idGen){
            this.idGen = idGen;
            return this;
        }

        public GenericControllerBuilder setPresenter(PresenterInterface presenter) {
            this.presenter = presenter;
            return this;
        }

        public GenericControllerBuilder setView(IView view) {
            this.view = view;
            return this;
        }

        public GenericController build(){
            return new GenericController(this.itemDb,
                    this.idGen,
                    this.presenter,
                    this.view);
        }

    }





}
