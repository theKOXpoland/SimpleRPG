package mc.theKOXpoland.SimpleRPG.Utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Util {
<<<<<<< HEAD
=======
    /*public static String fix(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }*/
>>>>>>> main

    public static Component mm(String text) {
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize(text);
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        if (min > max) {
            System.out.println("UTIL: min > max");
            return 0;
        } else if (max < 0) {
            System.out.println("UTIL: max < 0");
            return 0;
        }
        else
        return random.nextInt(max - min) + min;
    }

    public static double roundDouble(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
