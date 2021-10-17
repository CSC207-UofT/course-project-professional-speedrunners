package usecase.port.IResponse;

import java.util.List;

/**
 * Output data interface.
 * Presenters depend on the following methods to extract data from usecase response.
 */

public interface RespondModel{
    boolean getResult();
    List<String> getResponse();
    String getOperation();
}
