package usecase.port.IRequest.IItemIn;

import usecase.item.FindItem.*;

/**
 * Input port for FindItem usecase
 * This interface outlines what operations are supported for the particular usecase.
 * Controller objects should initiate FindItem object through the interface
 * IFindItem instead of the actual FindItem type
 */
public interface IFindItem {
    void findByStore(FindItemRequest store);
    void findById(FindItemRequest id);
    void findAll(FindItemRequest sort);
    FindItemRequest requestFindByStore(String storeId);
    FindItemRequest requestFindById(String itemId);
    FindItemRequest requestFindAll(String sorted);

}
