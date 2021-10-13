package usecase.port.IResponse;

import java.util.List;

public interface RespondModel{
    boolean getResult();
    List<String> getResponse();
    String getOperation();
}
