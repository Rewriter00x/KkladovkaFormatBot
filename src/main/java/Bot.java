import org.telegram.telegrambots.bots.*;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.*;

import java.io.*;

public class Bot extends TelegramLongPollingBot {

    private static final String USERNAME = "kkladovka_format_bot";
    private static final String TOKEN = getToken();

    private static String getToken() {
        String res = null;
        try {
            BufferedReader in = new BufferedReader(new FileReader("token.txt"));
            res = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res == null ? "" : res;
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            if (hasThreeLines(update.getMessage().getText())) {
                String[] msg = update.getMessage().getText().split("\n");
                String[] links = msg[0].split(" ");
                String[] words = msg[1].split(" ");
                if (links.length == words.length) {
                    StringBuilder text = new StringBuilder();
                    for (int i = 0; i < links.length; i++)
                        text.append("&lt <u><a href = '").append(links[i]).append("'>").append(words[i]).append("</a></u> &gt\n");
                    for (String tag : msg[2].split(" ")) text.append("<u>#").append(tag).append("</u> ");
                    text.append("<u>@kkladovka</u> \uD83C\uDF00");
                    try {
                        execute(new SendMessage(update.getMessage().getChatId().toString(), text.toString()) {
                            {
                                setParseMode("HTML");
                            }
                        });
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        execute(new SendMessage(update.getMessage().getChatId().toString(), "Error with input"));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            } else if (hasTwoLines(update.getMessage().getText())) {
                String[] msg = update.getMessage().getText().split("\n");
                String[] links = msg[0].split(" ");
                StringBuilder text = new StringBuilder();
                for (String link : links) text.append("&lt <u><a href = '").append(link).append("'>").append(getSrc(link)).append("</a></u> &gt\n");
                for (String tag : msg[1].split(" ")) text.append("<u>#").append(tag).append("</u> ");
                text.append("<u>@kkladovka</u> \uD83C\uDF00");
                try {
                    execute(new SendMessage(update.getMessage().getChatId().toString(), text.toString()) {
                        {
                            setParseMode("HTML");
                        }
                    });
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    execute(new SendMessage(update.getMessage().getChatId().toString(), "Error with input"));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean hasTwoLines(String text) {
        int n = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n') {
                n++;
                if (n == 2) return false;
            }
        }
        return n == 1;
    }

    public boolean hasThreeLines(String text) {
        int n = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n') {
                n++;
                if (n == 3) return false;
            }
        }
        return n == 2;
    }

    public String getSrc(String link) {
        switch (link.charAt(0)) {
            case 't':
                return "twi";

            case 'h':
                return getSrc(link.substring(8));

            case 'w':
            case '.':
                return getSrc(link.substring(1));

            case 'y':
                return "youtube";

            case 'v':
                return "tiktok";

            case 'p':
                if (link.charAt(1) == 'i') {
                    if (link.charAt(2) == 'n') return "pin";
                    if (link.charAt(2) == 'x') return "pixiv";
                }
                return "res";

            case 'i':
                return "inst";

            case 'r':
                return "reddit";

            default:
                return "res";
        }
    }
}
