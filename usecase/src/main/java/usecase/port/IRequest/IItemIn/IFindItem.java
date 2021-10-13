package usecase.port.IRequest.IItemIn;

import usecase.item.FindItem.*;

public interface IFindItem {
    void findByStore(FindItemRequest store);
    void findById(FindItemRequest id);
    void findAll(FindItemRequest sort);
    FindItemRequest requestFindByStore(String storeId);
    FindItemRequest requestFindById(String itemId);
    FindItemRequest requestFindAll(String sorted);

}
