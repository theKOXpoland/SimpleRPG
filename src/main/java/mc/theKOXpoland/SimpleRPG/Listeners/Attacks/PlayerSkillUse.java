package mc.theKOXpoland.SimpleRPG.Listeners.Attacks;

import mc.theKOXpoland.SimpleRPG.Managers.Attacks.SkillManager;
import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.Managers.Items.WeaponManager;
import mc.theKOXpoland.SimpleRPG.MainFile;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerSkillUse implements Listener {

    MainFile plugin;
    public PlayerSkillUse(MainFile plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAttack(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        if (damager instanceof Player player) {
            if (player.getAttackCooldown() >= 1) {
                ItemStack item = player.getInventory().getItemInMainHand();

                if (item.getType() == Material.AIR) {
                    return;
                }

                ItemMeta meta = item.getItemMeta();

                if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                    String NBTName = meta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

                    if (NBTName != null) {
                        for (String customKey : WeaponManager.customWeaponMap.keySet()) {
                            if (customKey.equals(NBTName)) {
                                CustomWeapon customWeapon = WeaponManager.customWeaponMap.get(customKey);
                                if (customWeapon == null) {
                                    return;
                                }

                                if (customWeapon.getCustomType().equals("Wand")) {
                                    event.setCancelled(true);
                                    SkillManager.getSkillMap().get("Spark").useSkill(player, item);
                                    return;
                                }

                                if (customWeapon.getCustomType().equals("Bow")) {
                                    event.setCancelled(true);
                                    return;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onAttackInteraction(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Action action = event.getAction();

        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            if (item == null) {
                return;
            }

            if (item.getType() == Material.AIR) {
                return;
            }

            ItemMeta meta = item.getItemMeta();

            if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                String NBTName = meta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

                if (NBTName != null) {
                    Player player = event.getPlayer();
                    if (player.getAttackCooldown() >= 1) {
                        for (String customKey : WeaponManager.customWeaponMap.keySet()) {
                            if (customKey.equals(NBTName)) {
                                CustomWeapon customWeapon = WeaponManager.customWeaponMap.get(customKey);
                                if (customWeapon == null) {
                                    return;
                                }
                                if (customWeapon.getCustomType().equals("Wand")) {
                                    event.setCancelled(true);
                                    SkillManager.getSkillMap().get("Spark").useSkill(player, item);
                                    return;
                                }

                                if (customWeapon.getCustomType().equals("Bow")) {
                                    event.setCancelled(true);
                                    return;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (item == null) {
                return;
            }

            if (item.getType() == Material.AIR) {
                return;
            }

            ItemMeta meta = item.getItemMeta();

            if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                String NBTName = meta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

                if (NBTName != null) {
                    Player player = event.getPlayer();
                    if (player.getAttackCooldown() >= 1) {
                        for (String customKey : WeaponManager.customWeaponMap.keySet()) {
                            if (customKey.equals(NBTName)) {
                                CustomWeapon customWeapon = WeaponManager.customWeaponMap.get(customKey);
                                if (customWeapon == null) {
                                    return;
                                }

                                if (customWeapon.getCustomType().equals("Bow")) {
                                    event.setCancelled(true);

                                    if (player.hasCooldown(item.getType())) {
                                        event.setCancelled(true);
                                        return;
                                    }

                                    SkillManager.getSkillMap().get("Arrow").useSkill(player, item);
                                    player.setCooldown(item.getType(), (int) SkillManager.getSkillMap().get("Arrow").getSkillCooldown()*20);
                                    return;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}
