package app;

import domain.*;

public class Controller {
    DataLists dataLists = new DataLists();
    
    public boolean validateUser(User account) {
        return dataLists.validateUser(account);
    }
}
