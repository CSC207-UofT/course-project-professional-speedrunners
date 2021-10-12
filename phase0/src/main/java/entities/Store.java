package entities;

import java.util.ArrayList;

public abstract class Store {
    String name;
    String location;
    ArrayList<Item> menu;

    public Store(String name, String location){
        this.name = name;
        this.location = location;
        this.menu = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Item> getMenu() {
        return menu;
    }

    public String getLocation() {
        return location;
    }

    public boolean updateLocation(String newLocation){
        this.location = newLocation;
        return true;
    }

}
