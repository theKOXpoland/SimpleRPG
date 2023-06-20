package mc.theKOXpoland.SimpleRPG.Managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    public void CooldownService(CooldownManager service) {
    }

    public static final Map<UUID, Double> cooldowns = new HashMap<>();

    public static void setCooldown(UUID player, Double time) {
        if (time <= 0) {
            cooldowns.remove(player);
        } else {
            cooldowns.put(player, time);
        }
    }

    public static boolean hasCooldown(UUID player) {
        return cooldowns.containsKey(player);
    }

    public static Double getCooldown(UUID player) {
        return cooldowns.getOrDefault(player, 0.0);
    }
}