package mc.theKOXpoland.SimpleRPG.Attacks.Skills.MainAttackReplace;

import mc.theKOXpoland.SimpleRPG.Attacks.Skills.SkillsInterface;
import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.CustomChecker;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SkillArrow implements SkillsInterface {

    private double Cooldown;

    MainFile plugin;
    public SkillArrow(MainFile plugin) {
        this.plugin = plugin;
    }

    private void getFromConfig() {
        if (plugin.configManager.getSkillConfig().getConfigurationSection("Skills.") == null) {
            plugin.getLogger().severe("Path to Skills. is invalid!");
            return;
        }

        ConfigurationSection cfg = plugin.configManager.getSkillConfig().getConfigurationSection("Skills.Arrow.");

        if (cfg == null) {
            plugin.getLogger().severe("CFG skills is null");
            return;
        }

        for (String skill : cfg.getKeys(false)) {

            double cooldown = 10.0;
            String cooldownPath = cfg.getString("Cooldown");

            if (cooldownPath != null) {
                try {
                    cooldown = Double.parseDouble(cooldownPath);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(skill + " Setting default cooldown to 10.0");
                }
            }

            Cooldown = cooldown;
        }
    }

    @Override
    public void useSkill(Player player, ItemStack item) {

        getFromConfig();

        CustomWeapon customWeapon = CustomChecker.getCustomWeapon(item);
        if (customWeapon == null) {
            return;
        }

        Arrow customArrow = (Arrow) player.getWorld().spawnEntity(player.getEyeLocation(), EntityType.ARROW);
        customArrow.setDamage(customWeapon.getDamage());
        customArrow.setVelocity(player.getEyeLocation().getDirection().multiply(2));
        customArrow.setPickupStatus(Arrow.PickupStatus.CREATIVE_ONLY);

        customArrow.setShooter(player);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (!customArrow.isDead()) {
                customArrow.remove();
            }
        }, 5 * 20);
    }

    @Override
    public double getSkillCooldown() {
        return Cooldown;
    }
}
