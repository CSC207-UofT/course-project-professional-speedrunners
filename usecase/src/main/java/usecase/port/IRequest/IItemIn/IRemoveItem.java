package usecase.port.IRequest.IItemIn;

import usecase.item.RemoveItem.*;
/**
 * Input port for RemoveItem usecase
 * This interface outlines what operations are supported for the particular usecase.
 */
public interface IRemoveItem {
    void remove(RemoveItemRequest request);
    RemoveItemRequest generateRequest(String itemId);
}
