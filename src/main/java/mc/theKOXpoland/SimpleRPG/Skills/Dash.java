package mc.theKOXpoland.SimpleRPG.Skills;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Dash {

    public static void dash(Player player) {
        Location loc = player.getLocation();
        Vector direction = loc.getDirection();

        double distance = 1.2;
        double volume = 0.5;

        player.setVelocity(direction.multiply(distance));
        player.setFallDistance(0);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 4, false, false, false));
        player.playSound(player, Sound.BLOCK_PISTON_EXTEND, 1, (float) volume);
    }
}