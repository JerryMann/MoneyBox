package by.jrr.telegrambot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public enum BotState {

    Start {
        @Override
        public void enter(BotContext context) {
            try{
                sendMessage(context, "");
            }catch (Exception e){
                System.out.println("TelegramApiValidationException");
            }

        }

        @Override
        public BotState nextState() {
            return CheckCommand;
        }
    },

    CheckCommand {
        @Override
        public void enter(BotContext context) {
            sendMessage(context, "You want to set your amount");
        }

        @Override
        public void handleInput(BotContext context) {
            String input = context.getInput();
            try {
                context.getPocket().setAmount(Double.parseDouble(input));
            } catch (NumberFormatException e) {
                System.out.println("command" + input);
            }
        }

        @Override
        public BotState nextState() {
            return InputValue;
        }
    },

    InputValue {
//        private BotState next;
        @Override
        public void enter(BotContext context) {
            sendMessage(context, "Set your amount:");
        }

        @Override
        public void handleInput(BotContext context) {
            String input = context.getInput();
            try {
                context.getPocket().setAmount(Double.parseDouble(input));
            } catch (NumberFormatException e) {
                System.out.println("command" + input);
            }
        }

        @Override
        public BotState nextState() {
            return Approved;
        }
    },


    Approved(false) {
        @Override
        public void enter(BotContext context) {
            sendMessage(context, "You've set: " +
                    context.getPocket().getAmount() + " as start amount\nThank you for application!");
        }

        @Override
        public BotState nextState() {
            return Start;
        }
    };

    private static BotState[] states;
    private final boolean inputNeeded;

    BotState() {
        this.inputNeeded = true;
    }

    BotState(boolean inputNeeded) {
        this.inputNeeded = inputNeeded;
    }

    public static BotState getInitialState() {
        return byId(0);
    }

    public static BotState byId(int id) {
        if (states == null) {
            states = BotState.values();
        }
        return states[id];
    }

    protected void sendMessage(BotContext context, String text) {
        SendMessage message = new SendMessage()
                .setChatId(context.getUser().getChatId())
                .setText(text);
        try {
            context.getBot().execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public boolean isInputNeeded() {
        return inputNeeded;
    }

    public void handleInput(BotContext context) {
        // do nothing by default
    }

    public abstract void enter(BotContext context);
    public abstract BotState nextState();
}
