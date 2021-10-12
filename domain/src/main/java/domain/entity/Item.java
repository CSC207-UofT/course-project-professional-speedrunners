package domain.entity;

public class Item implements PriceComparable{

    private float price;
    private Store store;
    private String id;

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

    public void setPrice(float price) {
        this.price = price;
    }

    public static ItemBuilder builder(){return new ItemBuilder();}

    public static class ItemBuilder{
        private float price;
        private Store store;
        private String id;

        public ItemBuilder setPrice(float price) {
            this.price = price;
            return this;
        }

        public ItemBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public ItemBuilder setStore(Store store) {
            this.store = store;
            return this;
        }

        public Item build(){return new Item(this.price, this.store, this.id);}

    }






}
