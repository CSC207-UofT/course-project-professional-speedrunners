package app.adapter.controller;

import app.adapter.controller.port.IView;
import app.adapter.controller.port.IViewCreateItem;
import app.adapter.controller.port.IViewFindItem;
import app.adapter.controller.port.IViewRemoveItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IResponse.PresenterInterface;
import usecase.port.IdGenerator;

public class GenericController  {

    private final ItemDb itemDb;
    private final IdGenerator idGen;
    private final PresenterInterface presenter;
    private final IView view;
    private final IViewCreateItem viewCreateItem;
    private final IViewFindItem viewFindItem;
    private final IViewRemoveItem viewRemoveItem;

    public GenericController(ItemDb itemDb, IdGenerator idGen, PresenterInterface presenter, IView view,
                             IViewCreateItem viewCreateItem, IViewFindItem viewFindItem, IViewRemoveItem viewRemoveItem){
        this.itemDb = itemDb;
        this.idGen = idGen;
        this.presenter = presenter;
        this.view = view;
        this.viewCreateItem = viewCreateItem;
        this.viewFindItem = viewFindItem;
        this.viewRemoveItem = viewRemoveItem;
    }

    public void run(){
        String operation;

        do{
            operation = view.getOperation();
            switch (operation) {
                case "1":
                    CreateItemController createItemController = new CreateItemController(itemDb, idGen, presenter,
                            viewCreateItem);
                    createItemController.createItem();
                    break;
                case "2":
                    FindItemController findItemsController = new FindItemController(itemDb, presenter);
                    findItemsController.findItems();
                    break;
                case "3":
                    DeleteItemController deleteItemController = new DeleteItemController(itemDb, presenter,
                            viewRemoveItem);
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
        private IViewCreateItem viewCreateItem;
        private IViewFindItem viewFindItem;
        private IViewRemoveItem viewRemoveItem;

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

        public GenericControllerBuilder setViewCreateItem(IViewCreateItem viewCreateItem) {
            this.viewCreateItem = viewCreateItem;
            return this;
        }

        public GenericControllerBuilder setViewFindItem(IViewFindItem viewFindItem) {
            this.viewFindItem = viewFindItem;
            return this;
        }

        public GenericControllerBuilder setViewRemoveItem(IViewRemoveItem viewRemoveItem) {
            this.viewRemoveItem = viewRemoveItem;
            return this;
        }

        public GenericController build(){
            return new GenericController(this.itemDb,
                    this.idGen,
                    this.presenter,
                    this.view,
                    this.viewCreateItem,
                    this.viewFindItem,
                    this.viewRemoveItem);
        }

    }





}
