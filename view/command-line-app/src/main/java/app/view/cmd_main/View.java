package app.view.cmd_main;

import app.adapter.controller.GenericController;
import app.adapter.controller.port.IView;
import app.adapter.controller.port.IViewCreateItem;
import app.adapter.controller.port.IViewFindItem;
import app.adapter.controller.port.IViewRemoveItem;

import java.util.Scanner;

public class View implements IView, IViewCreateItem, IViewRemoveItem, IViewFindItem {

    private final Scanner input;

    public View(){
        input = new Scanner(System.in);
    }
    @Override
    public String getItemName() {
        System.out.println("Enter the item Name:");
        return input.nextLine();
    }

    @Override
    public float getItemPrice() {
        System.out.println("Enter the item Price:");
        return Float.parseFloat(input.nextLine());
    }

    @Override
    public String getItemId() {
        System.out.println("Enter the item ID:");
        return input.nextLine();
    }

    @Override
    public String getStoreId() {
        System.out.println("Enter the itemId associated with the item:");
        return input.nextLine();
    }


    @Override
    public String getOperation() {
        System.out.println("Please select an operation:" +
                "\n 1: createItem" +
                "\n 2: findAll + Sort by price" +
                "\n 3: RemoveItem");
        return input.nextLine();
    }

    @Override
    public boolean getAnotherQuery() {
        System.out.println("Make another query? y/n");
        return input.nextLine().equals("y");
    }
}
