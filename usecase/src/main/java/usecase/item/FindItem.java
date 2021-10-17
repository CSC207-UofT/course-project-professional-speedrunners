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

/**
 * This class handle the usecase of finding items in the system.
 * It implements the IFindItem interface which defines what operations are supported by the usecase object
 * from a controller's perspective.
 */
public final class FindItem implements IFindItem {

    private final ItemDb repo;
    private final PresenterInterface presenter;

    /**
     * Initalize FindItem usecase by injecting dependencies
     * @param repo database object for handling item data
     * @param presenter a presenter object responsible for translating data from usecase readable format into view
     *                  object readable format (view model).
     */
    public FindItem(final ItemDb repo, PresenterInterface presenter) {
        this.repo = repo;
        this.presenter = presenter;
    }


    //usecase interactors
    /**
     * a usecase interactor.
     * the method searches the database and find items that belong to the specified store.
     * note that usecase methods passes the response directly to the presenter within the method instead of
     * generating a return type.
     * @param store an RequestModel object that contains the storeID to be matched with
     */
    @Override
    public void findByStore(FindItemRequest store) {
        List<? extends Item> lst = repo.findByStore(store.getStoreId());
        FindItemResponse response = new FindItemResponse("findByStore");

        _sort(store,lst);

        response.setLstResponse(lst);
        presenter.Show(response);
    }

    /**
     * a usecase interactor.
     * the method searches the database to find items that has matching item ID.
     * @param id an RequestModel object that contains the item ID to be matched with
     */
    @Override
    public void findById(FindItemRequest id) {
        FindItemResponse response = new FindItemResponse("findById");
        response.setItemResponse(repo.findById(id.getItemId()));
        presenter.Show(response);
    }

    /**
     * a usecase interactor.
     * the method returns a list of all items exist in the system.
     * user can optionally choose to have the returning list sorted by a user specified parameter.
     * @param sort an RequestModel object that whether the method should sort the return list and in what way.
     */
    @Override
    public void findAll(FindItemRequest sort) {
        FindItemResponse response = new FindItemResponse("findAll");
        List<? extends Item> lst = repo.findAll();
        _sort(sort, lst);

        response.setLstResponse(lst);
        presenter.Show(response);
    }

    /*TODO: make this public and have the option to sort handled through a controller
    this would need to be expanded by adding a central usecase handler and have entity specific
    usecase methods return the actual entities instead of immediately leaving through presenter.
    This structure allows composite requests to be handled
    e.g. findBystore -> reomveItem, findAll -> sort, etc.
    response will be generated in the central usecase handler once all operations are performed.
     */
    /**
     * a helper method that sort list of items.
     * this is a mutating method that mutates the input list.
     * @param request RequestModel that gives the instruction to sort and in what way
     * @param lst list of item to be sorted
     */
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

    /**
     * Generates an RequestModel object for findByStore operation
     * @param storeId storeID to be matched with
     * @return a FindItemRequest object
     */
    @Override
    public FindItemRequest requestFindByStore(String storeId) {
        return new FindItemRequest().buildStoreId(storeId);
    }
    /**
     * Generates an RequestModel object for findById operation
     * @param itemId id of the item to be matched with
     * @return a FindItemRequest object
     */
    @Override
    public FindItemRequest requestFindById(String itemId) {
        return new FindItemRequest().buildItemId(itemId);
    }

    /**
     * Generates an RequestModel object that indicates that the return list is to be sorted
     * @param sorted String that dictate what kind of sort to perform: "p" for price and "r" for rating
     * @return a FindItemRequest object
     */
    @Override
    public FindItemRequest requestFindAll(String sorted) {
        return new FindItemRequest().buildSorted(sorted);
    }


    //Request & respond models
    /**
     * A RequestModel object that contain parameters required that are readable to usecase to perform an operation.
     * FindItem requires a matching condition and an option to sort.
     */
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

    /**
     * A ResponseModel object that stores the result of the operation.
     * It is passed to the presenter by the usecase. The presenter then translate it into a view model by extracting
     * data from the RespondModel object. The methods needed to perform this extraction is defined in the RespondModel
     * interface.
     */
    public static class FindItemResponse implements RespondModel{
        private boolean success;
        private List<String> response;
        private final String operation;

        private FindItemResponse(String operation){
            this.success = false;
            this.response = new ArrayList<>();
            this.operation = operation;
        }

        /**
         * set up the object for a list return
         * @param lst the resulting list of item from find operations
         */
        private void setLstResponse(List<? extends Item> lst) {
            if (lst.isEmpty()){return;}

            List<String> lstStr = new ArrayList<>();
            for (Item i : lst) {lstStr.add(i.toString());}
            this.response = lstStr;
            this.success = true;

        }

        /**
         * set up the object for a single item return
         * @param item the resulting item from find operations
         */
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

