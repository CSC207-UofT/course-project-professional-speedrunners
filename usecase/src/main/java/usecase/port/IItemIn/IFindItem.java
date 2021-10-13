package usecase.port.IItemIn;

import usecase.item.FindItem.*;
import usecase.port.RespondModel;

public interface IFindItem {
    FindItemResponse findByStore(FindItemRequest store);
    FindItemResponse findById(FindItemRequest id);
    FindItemResponse findAll();
    FindItemRequest requestFindByStore(String storeId);
    FindItemRequest requestFindById(String itemId);

}
