package by.jrr.telegrambot.bot;

import by.jrr.telegrambot.model.Pocket;
import by.jrr.telegrambot.model.User;

public class BotContext {
    private final TelegramBot bot;
    private final User user;
    private final String input;
    private final Pocket pocket;

    public static BotContext of(TelegramBot bot, User user, String text, Pocket pocket) {
        return new BotContext(bot, user, text, pocket);
    }

    private BotContext(TelegramBot bot, User user, String input, Pocket pocket) {
        this.bot = bot;
        this.user = user;
        this.input = input;
        this.pocket = pocket;
    }

    public TelegramBot getBot() {
        return bot;
    }

    public User getUser() {
        return user;
    }

    public String getInput() {
        return input;
    }

    public Pocket getPocket() {
        return pocket;
    }
}
