package mc.theKOXpoland.SimpleRPG.Commands;

import mc.theKOXpoland.SimpleRPG.MainFile;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class SpiralTest implements CommandExecutor {

    MainFile plugin;
    public SpiralTest(MainFile plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        Player p = (Player) sender;
        Location location = p.getLocation();
        int radius = 2;

        final double[] locationY = {0};

        new BukkitRunnable() {
            @Override
            public void run() {
                double x = radius * Math.cos(locationY[0]);
                double z = radius * Math.sin(locationY[0]);
                p.spawnParticle(Particle.REDSTONE, location.add(x, locationY[0], z), 10, new Particle.DustOptions(Color.AQUA, 2.0F));
                locationY[0] = locationY[0] + 0.1;
                double loc = locationY[0] + 0.1;
                if (loc >= p.getLocation().getY() + 1) {
                    this.cancel();
                    System.out.println("ojoj");
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 10);

        return true;
    }
}