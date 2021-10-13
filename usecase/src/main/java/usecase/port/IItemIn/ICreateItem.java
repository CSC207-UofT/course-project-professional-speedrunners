package usecase.port.IItemIn;

import usecase.item.CreateItem.*;
import usecase.port.RequestModel;
import usecase.port.RespondModel;

public interface ICreateItem {
    CreateItemResponse execute(CreateItemRequest request);
    CreateItemRequest generateRequest(float price, String storeId);

}
