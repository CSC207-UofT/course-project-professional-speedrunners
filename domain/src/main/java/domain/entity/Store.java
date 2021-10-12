package domain.entity;

import java.util.ArrayList;
import java.util.List;

public class Store {
    String name;
    String location;
    List<Item> menu;

    public Store(String name, String location, List<Item> menu){
        this.name = name;
        this.location = location;
        this.menu = menu;
    }

    public Store(String name, String location){
        this.name = name;
        this.location = location;
        this.menu = new ArrayList<>();
    }



    public String getName() {
        return name;
    }

    public List<Item> getMenu() {
        return menu;
    }

    public String getLocation() {
        return location;
    }

    public boolean setLocation(String newLocation){
        this.location = newLocation;
        return true;
    }

    public boolean addItem(Item item){
        this.menu.add(item);
        return true;
    }

    public boolean removeItem(Item item){
        return this.menu.remove(item);
    }

    public static StoreBuilder builder(){return new StoreBuilder();}

    public static class StoreBuilder{
        String name;
        String location;
        List<Item> menu = new ArrayList<>();

        public StoreBuilder setLocation(String location) {
            this.location = location;
            return this;
        }

        public StoreBuilder setMenu(List<Item> menu) {
            this.menu = menu;
            return this;
        }

        public StoreBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Store build(){return new Store(this.name, this.location, this.menu);}

    }




}
