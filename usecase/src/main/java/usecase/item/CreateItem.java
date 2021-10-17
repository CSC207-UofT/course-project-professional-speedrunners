package usecase.item;

import domain.entity.Item;
import usecase.port.*;
import usecase.port.IDb.ItemDb;
import usecase.port.IRequest.IItemIn.ICreateItem;
import usecase.port.IRequest.RequestModel;
import usecase.port.IResponse.PresenterInterface;
import usecase.port.IResponse.RespondModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handle the usecase of creating item and adding it into the system.
 * It implements the ICreateItem interface which defines what operations are supported by the usecase object
 * from a controller's perspective.
 */
public final class CreateItem implements ICreateItem {

    private final ItemDb repo; // port for database dependency injection.
    private final IdGenerator idGen; // port for injecting ID generator implementations.
    private final PresenterInterface presenter; // port for injecting presenter implementations.

    /**
     * Initialize the Create Item usecase by injecting it with required dependencies.
     * @param repo a database object for handling item data
     * @param idGen a ID generator object responsible for generating unique id
     * @param presenter a presenter object responsible for translating data from usecase readable format into view
     *                  object readable format (view model).
     */
    public CreateItem(final ItemDb repo, final IdGenerator idGen, final PresenterInterface presenter) {
        this.repo = repo;
        this.idGen = idGen;
        this.presenter = presenter;
    }

    //TODO: how to create Ratable item

    /**
     * a usecase interactor.
     * the method responsible for performing item creation.
     * note that usecase methods passes the response directly to the presenter within the method instead of
     * generating a return type.
     * @param request an RequestModel object that contains the required parameters passed
     *                by the user interacting with the program.
     */
    @Override
    public void create(final CreateItemRequest request) {
        Item itemToAdd = Item.builder()
                .setPrice(request.getPrice())
                .setId(idGen.generate()) //unique id of the item is set by the id generator
                .setStoreId(request.getStoreId())
                .setName(request.getName())
                .build();
         presenter.Show(new CreateItemResponse(repo.add(itemToAdd), itemToAdd.toString(), "createItem"));
    }

    /**
     * Part of the ICreateItem interface that defines what parameters are necessary, and in what format, to perform the
     * create item operation.
     * This method would take in those parameters and generates a RequestModel object, which is then read by the usecase
     * method to perform its task.
     * In the case of create item operation, the parameters needed are the same as the ones needed in the Item object
     * constructor.
     * @param price price of the item in float
     * @param storeId id of the store in which the newly create item is a part of
     * @param name name of the item
     * @return a createItemRequest object
     */
    @Override
    public CreateItemRequest generateRequest(float price, String storeId, String name) {
        return new CreateItemRequest(price, storeId, name);
    }

    /**
     * A RequestModel object that contain parameters required that are readable to usecase to perform an operation.
     * create item operation requires the parameters from Item class constructor.
     */
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

    /**
     * A ResponseModel object that stores the result of the operation.
     * It is passed to the presenter by the usecase. The presenter then translate it into a view model by extracting
     * data from the RespondModel object. The methods needed to perform this extraction is defined in the RespondModel
     * interface.
     */
    public static class CreateItemResponse implements RespondModel {
        private final boolean result; //Whether the operation was successful or if error occurred
        private final List<String> response; //The list of string representation of objects that is returned from the query
        private final String operation; //What operation was performed

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
