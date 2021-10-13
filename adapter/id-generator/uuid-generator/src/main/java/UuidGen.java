package java;

import usecase.port.IdGenerator;
import java.util.UUID;


public class UuidGen implements IdGenerator {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
