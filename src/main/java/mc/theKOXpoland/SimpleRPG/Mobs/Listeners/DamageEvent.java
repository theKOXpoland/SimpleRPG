package mc.theKOXpoland.SimpleRPG.Mobs.Listeners;

import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Mobs.Customs.CustomMob;
import mc.theKOXpoland.SimpleRPG.Mobs.Managers.MobManager;
import mc.theKOXpoland.SimpleRPG.Others.Customs.CustomChecker;
import mc.theKOXpoland.SimpleRPG.Tasks.TargetPlayerTask;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;

public class DamageEvent implements Listener {

    private static MainFile plugin;
    public DamageEvent(MainFile plugin) {
        DamageEvent.plugin = plugin;
    }

    /*@EventHandler(priority = EventPriority.NORMAL)
    public void onDamageEvent(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity target = event.getEntity();

        if (damager.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            event.setCancelled(true);

            String nbtName = damager.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

            for (CustomMob customMob : MobManager.customMobsList) {
                if (customMob.getName().equals(nbtName)) {
                    if (target instanceof Damageable damageable) {
                        damageable.damage(customMob.getDamage());
                        break;
                    }
                }
            }

            if (target.getPersistentDataContainer().has(plugin.Key_NBT_Type)) {
                String nbtType = CustomChecker.mobHasType(target);
                if (nbtType != null) {
                    if (nbtType.contains("Hostile") || nbtType.contains("Neutral")) {
                        if (target instanceof Mob mob) {
                            TargetPlayerTask.target(mob, damager, plugin);
                        }
                    }
                }
            }
        }
    }*/

    @EventHandler
    public void onFallDamage(EntityDamageEvent even) {
        Entity entity = even.getEntity();
        EntityDamageEvent.DamageCause damageCause = even.getCause();

        if (entity.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {

            if (damageCause == EntityDamageEvent.DamageCause.FALL) {
                even.setCancelled(true);
            }
        }
    }
}
