package mc.theKOXpoland.SimpleRPG.Attacks.Skills.MainAttackReplace;

import mc.theKOXpoland.SimpleRPG.Managers.Attacks.DamageManager;
import mc.theKOXpoland.SimpleRPG.Attacks.Skills.SkillsInterface;
import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.CustomChecker;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SkillSpark implements SkillsInterface {

    MainFile plugin;
    public SkillSpark(MainFile plugin) {
        this.plugin = plugin;
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

        double magicDamage = DamageManager.calculateMagicDamage(item);

        player.getPersistentDataContainer().set(new NamespacedKey(plugin, "ParticleAttack"), PersistentDataType.INTEGER, 1);

        new BukkitRunnable() {
            int step = 0;

            @Override
            public void run() {
                if (step >= steps) {
                    this.cancel();
                    player.getPersistentDataContainer().set(new NamespacedKey(plugin, "ParticleAttack"), PersistentDataType.INTEGER, 0);
                    return;
                }

                currentLocation.add(direction);
                currentLocation.getWorld().spawnParticle(Particle.CRIT, currentLocation, 0);

                for (Entity entity : currentLocation.getNearbyEntities(0.5, 0.5, 0.5)) {
                    if (entity instanceof Damageable damageable) {
                        if (damageable == player) {
                            return;
                        }

                        damageable.damage(magicDamage, player);
                    }
                }
                step++;
            }
        }.runTaskTimer(plugin, 0L, 1L);
    }

    @Override
    public double getSkillCooldown() {
        return 0;
    }
}
