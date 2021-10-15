package usecase.item;

import domain.entity.Item;
import org.jetbrains.annotations.NotNull;
import usecase.port.*;
import usecase.port.IDb.ItemDb;
import usecase.port.IRequest.IItemIn.ICreateItem;
import usecase.port.IRequest.RequestModel;
import usecase.port.IResponse.PresenterInterface;
import usecase.port.IResponse.RespondModel;

import java.util.ArrayList;
import java.util.List;

public final class CreateItem implements ICreateItem {

    private final ItemDb repo;
    private final IdGenerator idGen;
    private final PresenterInterface presenter;

    public CreateItem(final ItemDb repo, final IdGenerator idGen, final PresenterInterface presenter) {
        this.repo = repo;
        this.idGen = idGen;
        this.presenter = presenter;
    }
    //TODO: how to create Ratable item
    @Override
    public void execute(final @NotNull CreateItemRequest request) {
        Item itemToAdd = Item.builder()
                .setPrice(request.getPrice())
                .setId(idGen.generate())
                .setStoreId(request.getStoreId())
                .setName(request.getName())
                .build();
         presenter.Show(new CreateItemResponse(repo.add(itemToAdd), itemToAdd.toString(), "createItem"));
    }

    @Override
    public CreateItemRequest generateRequest(float price, String storeId, String name) {
        return new CreateItemRequest(price, storeId, name);
    }

    public static class CreateItemRequest implements RequestModel {
        private final float price;
        private final String storeId;
        private final String name;

        public CreateItemRequest(float price, String storeId, String name){
            this.price = price;
            this.storeId = storeId;
            this.name = name;
        }

        public float getPrice() {
            return price;
        }

        public String getStoreId() {
            return storeId;
        }

        public String getName() {
            return name;
        }
    }
    public static class CreateItemResponse implements RespondModel {
        private final boolean result;
        private List<String> response;
        private String operation;

        private CreateItemResponse(boolean result, String item, String operation){
            this.result = result;
            this.response = new ArrayList<>();
            this.operation = operation;
            response.add(item);

        }
        @Override
        public boolean getResult() {
            return result;
        }

        @Override
        public List<String> getResponse() {
            return response;
        }

        @Override
        public String getOperation() {
            return operation;
        }


    }




}
