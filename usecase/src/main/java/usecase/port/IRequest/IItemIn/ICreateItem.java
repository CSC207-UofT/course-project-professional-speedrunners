package usecase.port.IRequest.IItemIn;

import usecase.item.CreateItem.*;

/**
 * Input port for CreateItem usecase
 * This interface outlines what operations are supported for the particular usecase.
 * Controller objects should initiate CreateItem object through the interface
 * ICreateItem instead of the actual CreateItem type
 */

public interface ICreateItem {
    void create(CreateItemRequest request);
    CreateItemRequest generateRequest(float price, String storeId, String name);

}
