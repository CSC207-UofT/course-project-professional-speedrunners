package usecase;

import usecase.item.ItemFacade;
import usecase.port.InputBoundary;
import usecase.port.OutputBoundary;
import usecase.sort.Sorter;
import usecase.store.StoreFacade;

public class UseCaseInteractor {

    private final ItemFacade itemFacade;
    private final StoreFacade storeFacade;
    private final Sorter sorter;
    private final InputBoundary request;
    private final OutputBoundary response;

    public UseCaseInteractor(InputBoundary request, OutputBoundary response){
        this.request = request;
        this.response = response;
        itemFacade = new ItemFacade();
        storeFacade = new StoreFacade();
        sorter = new Sorter();
    }












}
