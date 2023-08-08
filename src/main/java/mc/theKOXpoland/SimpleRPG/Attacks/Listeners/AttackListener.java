package mc.theKOXpoland.SimpleRPG.Attacks.Listeners;

import mc.theKOXpoland.SimpleRPG.Attacks.AttackEvents.ArrowHitEvent;
import mc.theKOXpoland.SimpleRPG.Attacks.AttackEvents.MagicHitEvent;
import mc.theKOXpoland.SimpleRPG.Attacks.AttackEvents.MeleeHitEvent;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Others.Customs.CustomChecker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import static mc.theKOXpoland.SimpleRPG.Attacks.Managers.DamageManager.*;

public class AttackListener implements Listener {

    private static MainFile plugin;
    public AttackListener(MainFile plugin) {
        AttackListener.plugin = plugin;
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player)) return;

        ItemStack weapon = player.getInventory().getItemInMainHand();

        if (event.getEntity() instanceof Damageable damageable) {
            if (isMeleeWeapon(weapon)) {
                if (isMagicWeapon(weapon, player)) {
                    event.setCancelled(true);
                } else {
                    double damage = calculateMeleeDamage(weapon, event.getDamage());
                    Bukkit.getPluginManager().callEvent(new MeleeHitEvent(player, damageable, damage));
                }
            }
            if (isMagicWeapon(weapon, player)) {
                boolean isParticleAttack;
                MagicHitEvent magicEvent = new MagicHitEvent(player, damageable, calculateMagicDamage(weapon), false);
                Bukkit.getPluginManager().callEvent(magicEvent);
                isParticleAttack = magicEvent.isParticleAttack();

                if (!isParticleAttack) {
                    double damage = calculateMagicDamage(weapon);
                    damageable.damage(damage, player);
                }
            }

            if (isBowWeapon(weapon)) {
                event.setCancelled(true);
                double damage = calculateArrowDamage(weapon);
                Bukkit.getPluginManager().callEvent(new ArrowHitEvent(player, damageable, damage));
            }
            event.setCancelled(true);
        }

    }

    private boolean isMeleeWeapon(ItemStack item) {

        if (item == null || item.getType() == Material.AIR) {
            return true;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return true;
        }

        String type = CustomChecker.itemHasType(meta);

        if (type == null) {
            return true;
        }

        return type.equals("Melee");
    }

    private boolean isMagicWeapon(ItemStack item, Player player) {

        if (item == null || item.getType() == Material.AIR) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return false;
        }

        String type = CustomChecker.itemHasType(meta);

        if (type == null) {
            return false;
        }

        Integer particleAttackValue = player.getPersistentDataContainer().get(new NamespacedKey(plugin, "ParticleAttack"), PersistentDataType.INTEGER);
        boolean isParticleAttack = particleAttackValue != null && particleAttackValue == 1;

        return type.equals("Wand") && isParticleAttack;
    }

    private boolean isBowWeapon(ItemStack item) {

        if (item == null || item.getType() == Material.AIR) {
            return true;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return true;
        }

        String type = CustomChecker.itemHasType(meta);

        if (type == null) {
            return true;
        }

        return type.equals("Bow");
    }

}