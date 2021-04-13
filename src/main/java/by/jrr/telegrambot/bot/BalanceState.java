package by.jrr.telegrambot.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public enum BalanceState {
    Start {
        @Override
        public void enter(BotContext context) {
            try{
                sendMessage(context, "Your balance is: " + context.getPocket().getAmount());
            }catch (Exception e){
                System.out.println("TelegramApiValidationException");
            }
        }

//        @Override
//        public BalanceState nextState() {
//            return CheckCommand;
//        }
//    },
//
//    CheckCommand {
//        private BalanceState next;
//
//        @Override
//        public void enter(BotContext context) {
//            sendMessage(context, "You want to set your amount");
//        }
//
//        @Override
//        public void handleInput(BotContext context) {
//            String input = context.getInput();
//            try {
//                context.getPocket().setAmount(Double.parseDouble(input));
//            } catch (NumberFormatException e) {
//                System.out.println("command" + input);
//            }
//        }
//
//        @Override
//        public BalanceState nextState() {
//            return GetBalanceValue;
//        }
//    },
//
//    GetBalanceValue {
//        //        private BotState next;
//        @Override
//        public void enter(BotContext context) {
//            sendMessage(context, "Set your amount:");
//        }
//
//        @Override
//        public void handleInput(BotContext context) {
//            String input = context.getInput();
//            try {
//                context.getPocket().setAmount(Double.parseDouble(input));
//            } catch (NumberFormatException e) {
//                System.out.println("command" + input);
//            }
//        }
//
//        @Override
//        public BalanceState nextState() {
//            return Approved;
//        }
//    },
//
//
//    Approved(false) {
//        @Override
//        public void enter(BotContext context) {
//            sendMessage(context, "You've set: " +
//                    context.getPocket().getAmount() + " as start amount\nThank you for application!");
//        }
//
//        @Override
//        public BalanceState nextState() {
//            return Start;
//        }
    };

    private static BalanceState[] states;
    private final boolean inputNeeded;

    BalanceState() {
        this.inputNeeded = true;
    }

    BalanceState(boolean inputNeeded) {
        this.inputNeeded = inputNeeded;
    }

    public static BalanceState getInitialState() {
        return byId(0);
    }

    public static BalanceState byId(int id) {
        if (states == null) {
            states = BalanceState.values();
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
//    public abstract BalanceState nextState();
}
