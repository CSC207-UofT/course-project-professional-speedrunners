package app.adapter.controller;

import app.adapter.presenter.GenericPresenter;
import app.adapter.id_generator.uuid.UuidGen;
import app.adapter.db.item_db.ItemDbHashmap;
import usecase.item.CreateItem;
import usecase.item.FindItem;
import usecase.item.RemoveItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IRequest.IItemIn.ICreateItem;
import usecase.port.IRequest.IItemIn.IFindItem;
import usecase.port.IRequest.IItemIn.IRemoveItem;
import usecase.port.IResponse.PresenterInterface;
import usecase.port.IdGenerator;


import java.util.Objects;
import java.util.Scanner;

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
        Scanner input = new Scanner(System.in);
        String ifLoop = "y";
        String option;
        while(Objects.equals(ifLoop, "y")){

            System.out.println("Please select an operation:" +
                    "\n 1: createItem" +
                    "\n 2: findAll + Sort by price" +
                    "\n 3: RemoveItem");

            option = input.nextLine();

            switch (option) {
                case "1":
                    float price;
                    String storeId;
                    String name;
                    System.out.println("Enter the item Name:");
                    name = input.nextLine();
                    System.out.println("Enter the item Price:");
                    price = input.nextFloat();
                    input.nextLine();
                    System.out.println("Enter the storeId associated with the item:");
                    storeId = input.nextLine();
                    ICreateItem createItem = new CreateItem(itemDb, idGen, presenter);
                    createItem.execute(createItem.generateRequest(price, storeId, name));
                    break;
                case "2":
                    IFindItem findItem = new FindItem(itemDb, presenter);
                    findItem.findAll(findItem.requestFindAll("p"));
                    break;
                case "3":
                    String itemId;
                    System.out.println("Enter the itemId associated with the item:");
                    itemId = input.nextLine();
                    IRemoveItem removeItem = new RemoveItem(itemDb, presenter);
                    removeItem.execute(removeItem.generateRequest(itemId));
                    break;
            }
            System.out.println("Make another query? y/n");
            ifLoop = input.nextLine();
        }





    }







}
