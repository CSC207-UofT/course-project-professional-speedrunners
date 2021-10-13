package usecase.item;

import domain.entity.Item;
import usecase.port.IRequest.IItemIn.IFindItem;
import usecase.port.IDb.ItemDb;
import usecase.port.IResponse.PresenterInterface;
import usecase.port.IRequest.RequestModel;
import usecase.port.IResponse.RespondModel;
import usecase.sort.Sorter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class FindItem implements IFindItem {

    private final ItemDb repo;
    private final PresenterInterface presenter;


    public FindItem(final ItemDb repo, PresenterInterface presenter) {
        this.repo = repo;
        this.presenter = presenter;
    }


    //usecase interactors
    @Override
    public void findByStore(FindItemRequest store) {
        List<? extends Item> lst = repo.findByStore(store.getStoreId());
        FindItemResponse response = new FindItemResponse("findByStore");

        _sort(store,lst);

        response.setLstResponse(lst);
        presenter.Show(response);
    }

    @Override
    public void findById(FindItemRequest id) {
        FindItemResponse response = new FindItemResponse("findById");
        response.setItemResponse(repo.findById(id.getItemId()));
        presenter.Show(response);
    }

    @Override
    public void findAll(FindItemRequest sort) {
        FindItemResponse response = new FindItemResponse("findAll");
        List<? extends Item> lst = repo.findAll();
        _sort(sort, lst);

        response.setLstResponse(lst);
        presenter.Show(response);
    }

    private void _sort(FindItemRequest request, List<? extends Item> lst){

        if(request.getSorted() == null){return;}

        Sorter sorter = new Sorter();
        if(request.getSorted().equals("p")){
            sorter.sortByPrice(lst);
        }else if(request.getSorted().equals("r")){
            //TODO:ensure lst are ratable and feed it to sorter
        }
    }

    //Request Generators
    @Override
    public FindItemRequest requestFindByStore(String storeId) {
        return new FindItemRequest().buildStoreId(storeId);
    }

    @Override
    public FindItemRequest requestFindById(String itemId) {
        return new FindItemRequest().buildItemId(itemId);
    }

    @Override
    public FindItemRequest requestFindAll(String sorted) {
        return new FindItemRequest().buildSorted(sorted);
    }


    //Request & respond models

    public static class FindItemRequest implements RequestModel{
        private String storeId;
        private String itemId;
        private String sorted;

        public FindItemRequest buildItemId(String itemId) {
            this.itemId = itemId;
            return this;
        }

        public FindItemRequest buildStoreId(String storeId) {
            this.storeId = storeId;
            return this;
        }

        public FindItemRequest buildSorted(String sorted) {
            this.sorted = sorted;
            return this;
        }

        public String getSorted(){return sorted;}

        public String getStoreId() {
            return storeId;
        }

        public String getItemId() {
            return itemId;
        }


    }
    public static class FindItemResponse implements RespondModel{
        private boolean success;
        private List<String> response;
        private final String operation;

        private FindItemResponse(String operation){
            this.success = false;
            this.response = new ArrayList<>();
            this.operation = operation;
        }

        private void setLstResponse(List<? extends Item> lst) {
            if (lst.isEmpty()){return;}

            List<String> lstStr = new ArrayList<>();
            for (Item i : lst) {lstStr.add(i.toString());}
            this.response = lstStr;
            this.success = true;

        }

        private void setItemResponse(Optional<Item> item) {
            if(item.isPresent()) {
                this.response.add(item.get().toString());
                this.success = true;
            }


        }

        @Override
        public boolean getResult() {
            return success;
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

