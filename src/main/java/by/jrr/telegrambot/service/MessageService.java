package by.jrr.telegrambot.service;

import by.jrr.telegrambot.model.Pocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class MessageService {

    public SendMessage onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        if(update != null) {
            Message message = update.getMessage();
            sendMessage.setChatId(message.getChatId());
            if(message != null && message.hasText()) {
                String magText = message.getText();
                if(magText.equals("/start")) {
                    return sendMessage.setText("Hello!");
                } else if (magText.equals("/settings")) {
                    return sendMessage.setText("Settings!");
                } else if (magText.equals("/help")) {
                    return sendMessage.setText("Help!");
                }else if (magText.equals("/amount")) {
                    return sendMessage.setText("Set amount, please");
                }
            }
        }
        return sendMessage.setText("Do no");
    }
}
