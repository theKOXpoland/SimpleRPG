package mc.theKOXpoland.SimpleRPG.Attacks.Skills.Skill;

import mc.theKOXpoland.SimpleRPG.Attacks.Skills.SkillsInterface;
import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.CustomChecker;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SkillTeleport implements SkillsInterface {

    private double distance;
    private double volume;
    private String sound;
    private String particle;
    private double cooldown;
    MainFile plugin;
    public SkillTeleport(MainFile plugin) {
        this.plugin = plugin;
    }

    private void getFromConfig() {

        if (plugin.configManager.getSkillConfig().getConfigurationSection("Skills.") == null) {
            plugin.getLogger().severe("Path to Skills. is invalid!");
            return;
        }

        ConfigurationSection cfg = plugin.configManager.getSkillConfig().getConfigurationSection("Skills.Teleport.");

        if (cfg == null) {
            plugin.getLogger().severe("CFG skills is null");
            return;
        }

        for (String skill : cfg.getKeys(false)) {

            double distance = 5;
            String distancePath = cfg.getString("Distance");

            if (distancePath != null) {
                try {
                    distance = Double.parseDouble(distancePath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(skill + " Setting default distance to 5");
                }
                if (distance <= 0) {
                    plugin.getLogger().severe(skill + " Setting default distance to 5");
                }
            }

            this.distance = distance;

            double volume = 0.5;
            String volumePath = cfg.getString("Volume");

            if (volumePath != null) {
                try {
                    volume = Double.parseDouble(volumePath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(skill + " Setting default volume to 0.5");
                }
                if (volume <= 0) {
                    plugin.getLogger().severe(skill + " Setting default volume to 0.5");
                }
            }

            this.volume = volume;

            String sound = "ENTITY_ENDERMAN_TELEPORT";
            String soundPath = cfg.getString("Sound");

            if (soundPath != null) {
                try {
                    sound = soundPath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(skill + " Setting default sound to BLOCK_PISTON_EXTEND");
                }
            }

            this.sound = sound;

            String particle = "PORTAL";
            String particlePath = cfg.getString("Particle");

            if (particlePath != null) {
                try {
                    particle = particlePath;
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(skill + " Setting default particle to PORTAL");
                }
            }

            this.particle = particle;

            double cooldown = 10.0;
            String cooldownPath = cfg.getString("Cooldown");

            if (cooldownPath != null) {
                try {
                    cooldown = Double.parseDouble(cooldownPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(skill + " Setting default cooldown to 10.0");
                }
            }

            this.cooldown = cooldown;
        }
    }

    @Override
    public void useSkill(Player player, ItemStack item) {

        Location location = player.getLocation().add(0, 1, 0);
        CustomWeapon customWeapon = CustomChecker.getCustomWeapon(item);
        if (customWeapon == null) {
            return;
        }
        Location endLocation = location.clone().add(location.getDirection().multiply(customWeapon.getItemRange()));

        Location currentLocation = location.clone();
        Vector direction = endLocation.toVector().subtract(location.toVector()).normalize();
        double distance = location.distance(endLocation);
        int steps = (int) Math.ceil(distance);

        player.getPersistentDataContainer().set(new NamespacedKey(plugin, "Teleport"), PersistentDataType.INTEGER, 1);

        new BukkitRunnable() {
            int step = 0;

            @Override
            public void run() {
                if (step >= steps) {
                    this.cancel();
                    player.getPersistentDataContainer().set(new NamespacedKey(plugin, "Teleport"), PersistentDataType.INTEGER, 0);
                    return;
                }

                currentLocation.add(direction);
                if (!currentLocation.getBlock().isCollidable()) {
                    currentLocation.getWorld().spawnParticle(Particle.FLAME, currentLocation, 0);
                }
                if (currentLocation.getBlock().isCollidable() || currentLocation.getBlock().isSolid()) {
                    return;
                }

                step++;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    @Override
    public double getSkillCooldown() {
        return cooldown;
    }
}
