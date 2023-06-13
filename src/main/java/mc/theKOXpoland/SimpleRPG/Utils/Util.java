package mc.theKOXpoland.SimpleRPG.Utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Util {
    public static String fix(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static Component mm(String text) {
        MiniMessage mm = MiniMessage.miniMessage();
        return mm.deserialize(text);
    }

    public static int getRandomNumber(int min, int max) {
        Random random = new Random();
        if (min > max) {
            System.out.println("UTIL: min > max");
            return random.nextInt(0);
        } else if (max < 0) {
            System.out.println("UTIL: max < 0");
            return random.nextInt(1);
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

    public static Location locFromString(String str) {
        String[] str2loc = str.split(":");
        Location loc = new Location((World) Bukkit.getWorlds().get(0), 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        loc.setX(Double.parseDouble(str2loc[0]));
        loc.setY(Double.parseDouble(str2loc[1]));
        loc.setZ(Double.parseDouble(str2loc[2]));
        loc.setYaw(Float.parseFloat(str2loc[3]));
        loc.setPitch(Float.parseFloat(str2loc[4]));
        loc.setWorld(Bukkit.getWorld(str2loc[5]));
        return loc;
    }
}
