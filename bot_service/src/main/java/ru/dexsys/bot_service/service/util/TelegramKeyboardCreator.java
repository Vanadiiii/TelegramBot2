package ru.dexsys.bot_service.service.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TelegramKeyboardCreator {
    public static InlineKeyboardMarkup createInlineKeyboard(
            List<String> elements, String buttonCommand, int high, int width
    ) {
        return new InlineKeyboardMarkup(
                Stream.iterate(0, n -> n + 1)
                        .limit(high)
                        .map(n -> Stream
                                .iterate(n + n * (width - 1), m -> m + 1)
                                .limit(width)
                                .filter(m -> m < elements.size())
                                .map(elements::get)
                                .map(InlineKeyboardButton::new)
                                .peek(button -> button.setCallbackData(buttonCommand + " " + button.getText()))
                                .collect(Collectors.toList())
                        )
                        .collect(Collectors.toList())
        );
    }

    public static InlineKeyboardMarkup createInlineKeyboard(String element, String buttonCommand) {
        return new InlineKeyboardMarkup(
                List.of(List.of(new InlineKeyboardButton(element).setCallbackData(buttonCommand)))
        );
    }

    public static ReplyKeyboardMarkup savePhoneReplyKeyboard(String sendDataText, String writePhoneText) {
        KeyboardButton button = new KeyboardButton()
                .setText(sendDataText)
                .setRequestContact(true);
        KeyboardButton button2 = new KeyboardButton()
                .setText(writePhoneText);

        List<KeyboardRow> keyboard = Stream.of(button, button2)
                .map(btn -> {
                    KeyboardRow keyboardRow = new KeyboardRow();
                    keyboardRow.add(btn);
                    return keyboardRow;
                })
                .collect(Collectors.toList());

        return new ReplyKeyboardMarkup()
                .setSelective(true)
                .setResizeKeyboard(true)
                .setOneTimeKeyboard(true)
                .setKeyboard(keyboard);
    }
}
