package usecase;

import usecase.port.IItemIn.IItem;
import usecase.port.PresenterInterface;
import usecase.port.RequestModel;
import usecase.port.RespondModel;

public class UseCaseInteractor {

    private final IItem inputBoundary;
    private final PresenterInterface outputBoundary;

    public UseCaseInteractor(IItem inputBoundary, PresenterInterface outputBoundary){
        this.inputBoundary = inputBoundary;
        this.outputBoundary = outputBoundary;
    }

    public RequestModel getRequest(){
       return inputBoundary.getRequest();
    }

    public RespondModel responseGenerator(RequestModel request){



    }

    public void display(RespondModel respond){
        outputBoundary.Show(respond);
    }















}
