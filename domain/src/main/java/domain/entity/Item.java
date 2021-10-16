package domain.entity;

/**
 * Class that represents an Item in the domain layer
 */
public class Item implements PriceComparable{

    //TODO: store store object instead of storeID?
    private float price;
    private final String storeId;
    private final String id;
    private String name;

    /**
     * Creates an Item with relevant parameters
     * storeId, id should be final upon creation
     * @param price the price of an PriceComparable object
     * @param storeId id of the store to which this item is associated with.
     * @param id unique id of the item
     * @param name name of the item
     */
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

    //TODO: add this setter into the PriceCOmparable interface
    /**
     * setter for Price. Mutating method
     * @param price new price of the item
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * setter for Price. Mutating method
     * @param name new name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * generates a String representation of the object.
     * This should include all fields represented in string format.
     * Currently, an item usecase returns a query response by adding the toString of the item to the response
     * since ResponseModel objects can only carry Strings at the moment.
     * This behaviour may change in the future.
     * @return String representation of the item
     */
    @Override
    public String toString(){
        return "Item: " + this.name + ", Price: " + this.price + ", Id: " + this.id;
    }

    /**
     * A builder class for item objects
     * This class is responsible for making construction of objects more readable in code, among other benefits.
     * It is not a necessary or important part of the class, but something nice to have.
     * Refer to builder design patterns for more detail
     * @return An ItemBuilder object
     */
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
