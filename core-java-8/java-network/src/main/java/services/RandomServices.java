package services;

import java.util.UUID;

@Service
public class RandomServices {

    public String randomUUID(){
        return UUID.randomUUID().toString();
    }
}
