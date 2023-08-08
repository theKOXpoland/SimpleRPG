package mc.theKOXpoland.SimpleRPG.Attacks.Skills;

import mc.theKOXpoland.SimpleRPG.Items.Customs.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Others.Customs.CustomChecker;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

        getFromConfig();

            CustomWeapon customWeapon = CustomChecker.getCustomWeapon(item);
            if (customWeapon == null) {
                return;
            }

            Vector direction = player.getLocation().getDirection().normalize();
            Vector start = player.getLocation().toVector();

            start.add(direction);

            for (int i = 0; i < customWeapon.getItemRange(); i++) {
                Block currentBlock = player.getWorld().getBlockAt(start.getBlockX(), start.getBlockY(), start.getBlockZ());

                if (currentBlock.getType().isSolid()) {
                    start.subtract(direction);
                } else {
                    player.teleport(currentBlock.getLocation().add(0.5, 1, 0.5));
                    return;
                }

                start.add(direction);
            }

            //Location endLocation = player.getLocation().add(direction.clone().multiply(customWeapon.getItemRange()));
            //player.teleport(endLocation);
    }

    @Override
    public double getSkillCooldown() {
        return cooldown;
    }
}
