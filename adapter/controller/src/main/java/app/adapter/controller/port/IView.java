package app.adapter.controller.port;

public interface IView {
    String getOperation();
    boolean getAnotherQuery();
    String getItemName();
    float getItemPrice();
    String getItemId();
    String getStoreId();
}
