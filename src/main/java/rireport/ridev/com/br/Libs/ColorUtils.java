package rireport.ridev.com.br.Libs;

import java.awt.*;

public class ColorUtils {

    public static Color getColor(String color){
        Color cor = null;
        if(color.startsWith("#")) {
            cor = Color.getColor(color);
        } else {
            switch (color.toLowerCase()) {
                case "white": {
                    cor = Color.WHITE;
                }
                case "gray": {
                    cor = Color.GRAY;
                }
                case "black": {
                    cor = Color.BLACK;
                }
                case "red": {
                    cor = Color.RED;
                }
                case "pink": {
                    cor = Color.PINK;
                }
                case "orange": {
                    cor = Color.ORANGE;
                }
                case "yellow": {
                    cor = Color.YELLOW;
                }
                case "green": {
                    cor = Color.GREEN;
                }
                case "magenta": {
                    cor = Color.MAGENTA;
                }
                case "cyan": {
                    cor = Color.CYAN;
                }
                case "blue": {
                    cor = Color.BLUE;
                }
            }
        }
        return cor;
    }
}
