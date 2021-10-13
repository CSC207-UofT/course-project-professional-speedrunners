package usecase.item;

import domain.entity.Item;
import org.jetbrains.annotations.NotNull;
import usecase.port.IItemIn.ICreateItem;
import usecase.port.IdGenerator;
import usecase.port.ItemDb;
import usecase.port.RequestModel;
import usecase.port.RespondModel;

public final class CreateItem implements ICreateItem {

    private final ItemDb repo;
    private final IdGenerator idGen;

    public CreateItem(final ItemDb repo, final IdGenerator idGen) {
        this.repo = repo;
        this.idGen = idGen;
    }
    //TODO: how to create Ratable item
    @Override
    public CreateItemResponse execute(final @NotNull CreateItemRequest request) {
        Item itemToAdd = Item.builder()
                .setPrice(request.getPrice())
                .setId(idGen.generate())
                .setStoreId(request.getStoreId())
                .build();
         return new CreateItemResponse(repo.add(itemToAdd), itemToAdd.getName(), itemToAdd.getId());
    }

    @Override
    public CreateItemRequest generateRequest(float price, String storeId) {
        return new CreateItemRequest(price, storeId);
    }

    public static class CreateItemRequest implements RequestModel{
        private final float price;
        private final String storeId;

        public CreateItemRequest(float price, String storeId){
            this.price = price;
            this.storeId = storeId;
        }

        public float getPrice() {
            return price;
        }

        public String getStoreId() {
            return storeId;
        }
    }
    public static class CreateItemResponse implements RespondModel {
        private final boolean result;
        private String name;
        private String id;

        public CreateItemResponse(boolean result, String name, String id){
            this.result = result;
            this.name = name;
            this.id = id;
        }
        @Override
        public boolean getResult() {
            return result;
        }
        public String getName() {
            return name;
        }
        public String getId() {
            return id;
        }
    }




}
