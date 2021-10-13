package usecase.port.IRequest.IItemIn;

import usecase.item.RemoveItem.*;

public interface IRemoveItem {
    void execute(RemoveItemRequest request);
    RemoveItemRequest generateRequest(String itemId);
}
