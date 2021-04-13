package by.jrr.telegrambot.dbDefault;

import by.jrr.telegrambot.model.User;

public class dbDefaukt {

    public User getUser(){
        User user = new User();
        user.setId(null);
        return user;
    }

}
