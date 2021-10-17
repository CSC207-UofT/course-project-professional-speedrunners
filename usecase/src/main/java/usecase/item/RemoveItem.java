package usecase.item;

import domain.entity.Item;
import usecase.port.IRequest.IItemIn.IRemoveItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IResponse.PresenterInterface;
import usecase.port.IRequest.RequestModel;
import usecase.port.IResponse.RespondModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class handle the usecase of removing items in the system.
 * It implements the IRemoveItem interface which defines what operations are supported by the usecase object
 * from a controller's perspective.
 */
public final class RemoveItem implements IRemoveItem {

    private final ItemDb repo;
    private final PresenterInterface presenter;

    /**
     * Initalize RemoveItem usecase by injecting dependencies
     * @param repo database object for handling item data
     * @param presenter a presenter object responsible for translating data from usecase readable format into view
     *                  object readable format (view model).
     */
    public RemoveItem(final ItemDb repo, PresenterInterface presenter) {
        this.repo = repo;
        this.presenter = presenter;
    }

    //usecase interactors
    /**
     * a usecase interactor.
     * the method removes an item from database that has the matching itemId
     * @param request an RequestModel object that contains the storeID to be matched with
     */
    @Override
    public void remove(RemoveItemRequest request) {
        RemoveItemResponse response = new RemoveItemResponse("removeItem");
        response.setResponse(repo.remove(request.getItemId()));
        presenter.Show(response);
    }

    // Request Generator
    /**
     * Generates an RequestModel object for remove operation
     * @param itemId ID of the item to be removed
     * @return a RemoveItemRequest object
     */
    @Override
    public RemoveItemRequest generateRequest(String itemId){
        return new RemoveItemRequest(itemId);
    }

    // Request & respond models
    /**
     * A RequestModel object that contain parameters required that are readable to usecase to perform an operation.
     * RemoveItem requires the id of the item to be removed.
     */
    public static class RemoveItemRequest implements RequestModel{
        private final String itemId;

        public RemoveItemRequest(String itemId){
            this.itemId = itemId;
        }

        public String getItemId() {
            return itemId;
        }
    }

    /**
     * A ResponseModel object that stores the result of the operation.
     * It is passed to the presenter by the usecase. The presenter then translate it into a view model by extracting
     * data from the RespondModel object. The methods needed to perform this extraction is defined in the RespondModel
     * interface.
     */
    public static class RemoveItemResponse implements RespondModel{
        private boolean success;
        private final List<String> response;
        private final String operation;

        private RemoveItemResponse(String operation){
            this.success = false;
            this.response = new ArrayList<>();
            this.operation = operation;
        }


        private void setResponse(Optional<Item> item) {
            if(item.isPresent()) {
                this.response.add(item.get().toString());
                this.success = true;
            }
        }

        @Override
        public List<String> getResponse() {
            return response;
        }

        @Override
        public String getOperation() {
            return operation;
        }

        @Override
        public boolean getResult() {
            return success;
        }

    }

}
