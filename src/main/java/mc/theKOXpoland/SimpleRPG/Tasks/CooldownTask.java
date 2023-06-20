package mc.theKOXpoland.SimpleRPG.Tasks;

import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Managers.CooldownManager;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CooldownTask extends BukkitRunnable {

    MainFile plugin;
    public CooldownTask(MainFile plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        if (CooldownManager.cooldowns.isEmpty()) {
            this.cancel();
            return;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            Double timeLeft = CooldownManager.getCooldown(player.getUniqueId());
            if (timeLeft > 0) {
                CooldownManager.setCooldown(player.getUniqueId(), --timeLeft);
                player.sendActionBar(Util.mm("<dark_red><bold>! <red>Attack <grey>Cooldown: <dark_grey>" + Util.roundDouble(timeLeft, 2)
                        + " <dark_red><bold>!<reset>"));
            }
            if (timeLeft <= 0) {
                player.sendActionBar(Util.mm("<green><bold>Cooldown <green>is no more!"));
                this.cancel();
            }
        }
    }
}
