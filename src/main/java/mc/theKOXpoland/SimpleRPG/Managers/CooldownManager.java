package mc.theKOXpoland.SimpleRPG.Managers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class CooldownManager {

    private static final Map<UUID, TimeWrapper> cooldowns = new HashMap<>();

    public static Map<UUID, TimeWrapper> getCooldowns() {
        clearMap();
        return cooldowns;
    }

    private static void clearMap() {
        Iterator<Map.Entry<UUID, TimeWrapper>> iterator = cooldowns.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<UUID, TimeWrapper> next = iterator.next();
            TimeWrapper timeWrapper = next.getValue();

            if (System.currentTimeMillis() - timeWrapper.getAttachTime() > timeWrapper.getCooldown() * 1000) {
                iterator.remove();
            }
            Player player = Bukkit.getPlayer(next.getKey());
            if (player != null) {
                player.sendActionBar(Util.mm(""));
            }
        }
    }

    public static void setCooldown(UUID player, long time) {
        cooldowns.put(player, new TimeWrapper(time, System.currentTimeMillis()));
    }

    public static boolean hasCooldown(UUID player) {
        TimeWrapper timeWrapper = cooldowns.get(player);

        if (timeWrapper == null) {
            return false;
        }

        return System.currentTimeMillis() - timeWrapper.getAttachTime() <= timeWrapper.getCooldown() * 1000;
    }

    public static long getCooldown(UUID player) {
        TimeWrapper timeWrapper = cooldowns.get(player);

        if (timeWrapper == null) {
            return 0;
        }

        return (timeWrapper.getCooldown() * 1000 - (System.currentTimeMillis() - timeWrapper.getAttachTime())) / 1000;
    }

    @AllArgsConstructor
    @Getter
    public static class TimeWrapper {
        private final long cooldown;
        private final long attachTime;
    }
}