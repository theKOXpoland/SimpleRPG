package mc.theKOXpoland.SimpleRPG.Attacks.Skills.Skill;

import mc.theKOXpoland.SimpleRPG.Attacks.Skills.SkillsInterface;
import mc.theKOXpoland.SimpleRPG.MainFile;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class SkillDash implements SkillsInterface {
    private double distance;
    private double volume;
    private String sound;
    private double cooldown;

    MainFile plugin;
    public SkillDash(MainFile plugin) {
        this.plugin = plugin;
    }

        private void getFromConfig() {
            if (plugin.configManager.getSkillConfig().getConfigurationSection("Skills.") == null) {
                plugin.getLogger().severe("Path to Skills. is invalid!");
                return;
            }

            ConfigurationSection cfg = plugin.configManager.getSkillConfig().getConfigurationSection("Skills.Dash");

            if (cfg == null) {
                plugin.getLogger().severe("CFG skills is null");
                return;
            }

            for (String skill : cfg.getKeys(false)) {

                double distance = 1.2;
                String distancePath = cfg.getString("Distance");

                if (distancePath != null) {
                    try {
                        distance = Double.parseDouble(distancePath);
                    } catch (IllegalArgumentException exp) {
                        plugin.getLogger().severe(skill + " Setting default distance to 1.2");
                    }
                    if (distance <= 0) {
                        plugin.getLogger().severe(skill + " Setting default distance to 1.2");
                        distance = 1.2;
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
                        volume = 0.5;
                    }
                }

                this.volume = volume;

                String sound = "BLOCK_PISTON_EXTEND";
                String soundPath = cfg.getString("Sound");

                if (soundPath != null) {
                    try {
                        sound = soundPath;
                    } catch (IllegalArgumentException exp) {
                        plugin.getLogger().severe(skill + " Setting default sound to BLOCK_PISTON_EXTEND");
                    }
                }

                    this.sound = sound;

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

            Location loc = player.getLocation();
            Vector direction = loc.getDirection();

            player.setVelocity(direction.multiply(distance));
            player.setFallDistance(0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 60, 4, false, false, false));
            player.playSound(player, Sound.valueOf(sound), 1, (float) volume);

        }

    @Override
    public double getSkillCooldown() {
        return cooldown;
    }
}