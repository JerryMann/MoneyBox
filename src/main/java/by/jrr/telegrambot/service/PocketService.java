package by.jrr.telegrambot.service;

import by.jrr.telegrambot.model.Pocket;
import by.jrr.telegrambot.model.User;
import org.springframework.stereotype.Service;

@Service
public class PocketService {

    public void createPocket(User user) {

    }

    public double getBalance() {
        return 45678.9;
    }

    public void setAmount(double amount) {
        Pocket pocket = new Pocket();
        pocket.setAmount(amount);
        System.out.println("in logic setAmount " + amount);
    }

    public void setMaxValue(double maxAmount) {
        Pocket pocket = new Pocket();
        pocket.setMaxAmount(maxAmount);
        //logic
    }

    public void setSpend(Pocket pocket, double usage) {

//        pocket.getAmount() - usage;
    }

    public void checkAmount(double maxAmount, Pocket pocket) {
//        pocket.getAmount() - maxAmount;
    }

}
