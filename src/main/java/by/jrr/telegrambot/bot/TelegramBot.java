package by.jrr.telegrambot.bot;

import by.jrr.telegrambot.service.MessageService;
import by.jrr.telegrambot.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.File;
import java.io.IOException;

@Component
@PropertySource("application.properties")
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.username}")
    private String botUsername;
    @Value("${bot.token}")
    private String botToken;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;


    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = messageService.onUpdateReceived(update);
        try {
            messageService.setButtons(sendMessage);
            try{
                execute(sendMessage);
            }catch (TelegramApiValidationException e){
                System.out.println("TelegramApiValidationException");
            }
        } catch (TelegramApiException e ) {
            e.printStackTrace();
        }
    }

    private void saveJson(Update update) {
        try {
            objectMapper.writeValue(new File("src/test/resources/update.json"),update);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

//    public SendMessage sendMsg(Message message, String text){
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.enableMarkdown(true);
//        sendMessage.setChatId(message.getChatId().toString());
//        sendMessage.setReplyToMessageId(message.getMessageId());
//        sendMessage.setText(text);
//        return sendMessage;
//    }

}
