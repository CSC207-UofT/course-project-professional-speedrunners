package domain.entity;

public class Item implements PriceComparable{

    private float price;
    private final String storeId;
    private final String id;
    private final String name;

    public Item(float price, String storeId, String id, String name){
        this.price = price;
        this.storeId = storeId;
        this.id = id;
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public String getStoreId() {
        return storeId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return Float.toString(this.price);
    }

    //TODO: Implement toString for entity classes
    public static ItemBuilder builder(){return new ItemBuilder();}

    public static class ItemBuilder{
        private float price;
        private String storeId;
        private String id;
        private String name;

        public ItemBuilder setPrice(float price) {
            this.price = price;
            return this;
        }

        public ItemBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public ItemBuilder setStoreId(String storeId) {
            this.storeId = storeId;
            return this;
        }

        public ItemBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public Item build(){return new Item(this.price, this.storeId, this.id, this.name);}

    }






}
