package rireport.ridev.com.br.Libs;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public final class FancyText {
    public static final Pattern COLOR_PATTERN = Pattern.compile("(?i)(&)[0-9A-FK-OR]");

    public static String colored(@NotNull String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String[] colored(@NotNull String... messages) {
        for (int i = 0; i < messages.length; i++) {
            messages[i] = colored(messages[i]);
        }

        return messages;
    }

    public static List<String> colored(@NotNull List<String> description) {

        return description.stream()
                .map(FancyText::colored)
                .collect(Collectors.toList());

    }

    public static java.awt.Color getColorByHex(String hex) {
        return java.awt.Color.decode(hex);
    }

    public static String getFirstColor(String input) {
        Matcher matcher = COLOR_PATTERN.matcher(input);
        String first = "";
        if (matcher.find()) {
            first = matcher.group();
        }

        return FancyText.colored(first);
    }

    public static String formatNumber(int number) {
        StringBuilder sb = new StringBuilder(String.valueOf(number));

        if (sb.length() > 3) {
            for (int i = (sb.length() - 3); i > 0; i = i - 3) {
                sb.insert(i, ".");
            }
            return sb.toString();
        } else {
            return String.valueOf(number);
        }
    }

}