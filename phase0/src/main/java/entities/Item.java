package entities;

public abstract class Item {

    float price;
    Store store;
    String id;

    public Item(float price, Store store, String id){
        this.price = price;
        this.store = store;
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public Store getStore() {
        return store;
    }

    public boolean updatePrice(float newPrice){
        this.price = newPrice;
        return true;
    }

}
