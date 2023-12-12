package mc.theKOXpoland.SimpleRPG.Tasks;

import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Managers.CooldownManager;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class CooldownTask extends BukkitRunnable {

    MainFile plugin;
    public CooldownTask(MainFile plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        if (CooldownManager.getCooldowns().isEmpty()) {
            return;
        }

        for (UUID uuid : CooldownManager.getCooldowns().keySet()) {
            Player player = Bukkit.getPlayer(uuid);

            if (player == null) {
                continue;
            }

            long cooldown = CooldownManager.getCooldown(uuid);

            if (cooldown+1 > 0) {
                player.sendActionBar(Util.mm("<dark_red><bold>! <red>Attack <grey>Cooldown: <dark_grey>" + Util.roundDouble(cooldown+1, 2)
                        + " <dark_red><bold>!<reset>"));
            }
        }
    }
}
