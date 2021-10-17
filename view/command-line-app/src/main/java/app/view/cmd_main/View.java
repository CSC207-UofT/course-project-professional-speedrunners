package app.view.cmd_main;

import app.adapter.controller.GenericController;
import app.adapter.controller.port.IViewCreateItem;
import app.adapter.controller.port.IViewFindItem;
import app.adapter.controller.port.IViewRemoveItem;

import java.util.Scanner;

public class View implements IViewCreateItem, IViewRemoveItem, IViewFindItem {

    @Override
    public String getItemName() {
        return null;
    }

    @Override
    public float getItemPrice() {
        return 0;
    }

    @Override
    public String getItemId() {
        return null;
    }

    @Override
    public String getStoreId() {
        return null;
    }


}
