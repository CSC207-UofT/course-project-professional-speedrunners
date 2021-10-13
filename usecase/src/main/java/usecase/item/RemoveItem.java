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

public final class RemoveItem implements IRemoveItem {

    private final ItemDb repo;
    private final PresenterInterface presenter;


    public RemoveItem(final ItemDb repo, PresenterInterface presenter) {
        this.repo = repo;
        this.presenter = presenter;
    }


    @Override
    public void execute(RemoveItemRequest request) {
        RemoveItemResponse response = new RemoveItemResponse("removeItem");
        response.setResponse(repo.remove(request.getItemId()));
        presenter.Show(response);
    }

    // Request Generator
    @Override
    public RemoveItemRequest generateRequest(String itemId){
        return new RemoveItemRequest(itemId);
    }

    // Models
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

    public static class RemoveItemRequest implements RequestModel{
        private final String itemId;

        public RemoveItemRequest(String itemId){
            this.itemId = itemId;
        }

        public String getItemId() {
            return itemId;
        }
    }

}
