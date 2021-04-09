package by.jrr.telegrambot.service;

import by.jrr.telegrambot.model.Pocket;
import org.springframework.stereotype.Service;

@Service
public class PocketService {

    public void setAmount(double amount){
        Pocket pocket = new Pocket();
        pocket.setAmount(amount);
        //logic
    }

    public void setMaxValue(double maxAmount){
        Pocket pocket = new Pocket();
        pocket.setMaxAmount(maxAmount);
        //logic
    }

    public void setSpend(Pocket pocket, double usage){

//        pocket.getAmount() - usage;
    }

    public void checkAmount(double maxAmount, Pocket pocket){
//        pocket.getAmount() - maxAmount;
    }

}
