package by.jrr.telegrambot.service;

import by.jrr.telegrambot.bot.BalanceState;
import by.jrr.telegrambot.bot.BotContext;
import by.jrr.telegrambot.bot.BotState;
import by.jrr.telegrambot.bot.TelegramBot;
import by.jrr.telegrambot.model.Pocket;
import by.jrr.telegrambot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    UserService userService;
    @Autowired
    TelegramBot telegramBot;
    @Autowired
    PocketService pocketService;

    public SendMessage onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        if (update != null) {
            Message message = update.getMessage();
            sendMessage.setChatId(message.getChatId());
            if (message != null && message.hasText()) {
                int i;
                String msgText = message.getText();
                switch (msgText) {
                    case "/start":
//                        nom(update, i);
                        return sendMessage.setText("Pocket setting");
                    case "/set amount":
                        i = 1;
                        nom(update, i);
                        return sendMessage.setText("");
                    case "/get balance":
                        i = 3;
                        nom(update, i);
                        return sendMessage.setText("Getting Balance");
                    case "/set max amount":
                        return sendMessage.setText("Setting max amount");
                    default:
                        i = 2;
                        nom(update, i);
                        return sendMessage.setText("What is the next step?");
                }
            }
        }
        return sendMessage.setText("Didn't find");
    }

    public void setButtons(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/start"));
        keyboardFirstRow.add(new KeyboardButton("/set amount"));
        keyboardFirstRow.add(new KeyboardButton("/get balance"));
        keyboardFirstRow.add(new KeyboardButton("/set max amount"));

        keyboard.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void nom(Update update, int i) {
        final String text = update.getMessage().getText();
        final long chatId = update.getMessage().getChatId();

        User user = userService.findByChatId(chatId);
        Pocket pocket = new Pocket();


//        switch (i) {
//            case 0:
//
//            case 1:
                BotContext context;
                BotState state;

                if (user == null) {
                    state = BotState.byId(i);

                    user = new User(chatId, state.ordinal());
                    userService.addUser(user);
                    pocketService.createPocket(user);

                    context = BotContext.of(this.telegramBot, user, text, pocket);
                    state.enter(context);

                } else {
                    context = BotContext.of(this.telegramBot, user, text, pocket);
                    state = BotState.byId(user.getStateId());

                }

                state.handleInput(context);

                do {
                    state = state.nextState();
                    state.handleInput(context);
                    state.enter(context);
                } while (!state.isInputNeeded());

                user.setStateId(state.ordinal());
                userService.updateUser(user);
                pocketService.setAmount(pocket.getAmount());

//            case 2:
//
//                    context = BotContext.of(this.telegramBot, user, text, pocket);
//                    state = BotState.byId(user.getStateId());
//
//
//
//                state.handleInput(context);
//
//                do {
//                    state = state.nextState();
//                    state.handleInput(context);
//                    state.enter(context);
//                } while (!state.isInputNeeded());
//
//                user.setStateId(state.ordinal());
//                userService.updateUser(user);
//                pocketService.setAmount(pocket.getAmount());
//
//            case 3:
//                pocket.setAmount(pocketService.getBalance());
//                BalanceState balanceState;
//                BotContext botContext;
//                botContext = BotContext.of(this.telegramBot, user, text, pocket);
//                balanceState = BalanceState.getInitialState();
//                balanceState.handleInput(botContext);

//        }
    }
}
