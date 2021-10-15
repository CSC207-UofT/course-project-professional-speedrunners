package usecase.port.IRequest.IItemIn;

import usecase.item.CreateItem.*;

public interface ICreateItem {
    void execute(CreateItemRequest request);
    CreateItemRequest generateRequest(float price, String storeId, String name);

}
