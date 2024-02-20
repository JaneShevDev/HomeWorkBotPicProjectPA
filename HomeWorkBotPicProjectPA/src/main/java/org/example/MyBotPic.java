package org.example;

import net.thauvin.erik.crypto.CryptoPrice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.Scanner;

public class MyBotPic extends TelegramLongPollingBot {
    public MyBotPic() {
        super("6874191171:AAGMiAq4rIWNC7Rba-jc4K_70bxIVyJQmgU");
    }

    @Override
    public void onUpdateReceived(Update update) {
        var chatId = update.getMessage().getChatId();
        var text = update.getMessage().getText();

        try {
            if (text.equals("/start")) {
                sendMessage(chatId, "Hello!");
            } else if (text.equals("btc")) {
                sendPicture(chatId, "BTC.jpg");
                sendPrice(chatId, "BTC");
                cryptoConverter(chatId,"BTC");
            } else if (text.equals("eth")) {
                sendPicture(chatId, "ETH.jpg");
                sendPrice(chatId, "ETH");
                cryptoConverter(chatId,"ETH");
            } else if (text.equals("ltc")) {
                sendPicture(chatId, "LTC.jpg");
                sendPrice(chatId, "LTC");
                cryptoConverter(chatId,"LTC");
            } else {
                sendMessage(chatId, "Unknown command!");
            }
        } catch (Exception e) {
            System.out.println("Error!");
        }
    }
     void sendPrice(long chatId, String name) throws Exception {
        var price = CryptoPrice.spotPrice(name);
        sendMessage(chatId, name + " price: " + price.getAmount().doubleValue());
    }

    void sendPicture(long chatId, String name) throws Exception {
        var photo = getClass().getClassLoader().getResourceAsStream(name);

        var message = new SendPhoto();
        message.setChatId(chatId);
        message.setPhoto(new InputFile(photo, name));
        execute(message);
    }

    void sendMessage(long chatId, String text) throws Exception {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        execute(message);
    }
    void cryptoConverter(long chatId, String name) throws  Exception {
            var priceCry = CryptoPrice.spotPrice(name);
            sendMessage(chatId, " How mach $ do you want to ex?" );
            var scanner = new Scanner(System.in);
            var dollars = scanner.nextDouble();
            double result = dollars /  priceCry.getAmount().doubleValue();
            sendMessage(chatId,"You wll recve: " + result + name);
        }

    @Override
    public String getBotUsername() {
        return "YevheniiaSh_HW_Bot";
    }
}