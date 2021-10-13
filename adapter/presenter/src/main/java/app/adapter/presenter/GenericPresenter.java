package app.adapter.presenter;

import usecase.port.IResponse.RespondModel;
import usecase.port.IResponse.PresenterInterface;

public class GenericPresenter implements PresenterInterface {
    @Override
    public void Show(RespondModel respond) {
        if(respond.getResult()){
            //TODO: also show if a sorting operation was requested
            System.out.println(String.format("Operation %s Success!", respond.getOperation()));
            System.out.println(respond.getResponse());
        }else{
            //Todo: RespondModel should also be responsible for carrying exceptions across boundary
            System.out.println(String.format("Operation %s failed!", respond.getOperation()));
        }

    }
}
