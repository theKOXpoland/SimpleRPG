package mc.theKOXpoland.SimpleRPG.Attacks.Listeners;

import mc.theKOXpoland.SimpleRPG.Items.Customs.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Others.Managers.CooldownManager;
import mc.theKOXpoland.SimpleRPG.Attacks.Managers.SkillManager;
import mc.theKOXpoland.SimpleRPG.Attacks.Skills.SkillsInterface;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.Random;

public class WeaponSkill implements Listener {

    private final MainFile plugin;
    public WeaponSkill(MainFile plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack item = event.getItem();

            if (item == null || item.getType() == Material.AIR) {
                return;
            }

            ItemMeta meta = item.getItemMeta();

            if (meta.hasEnchants()) {
                if (item.getType() == Material.CHEST) {
                    event.setCancelled(true);

                    int upper = CustomWeapon.customWeaponsList.size();
                    Random random = new Random();

                    player.getInventory().addItem(CustomWeapon.customWeaponsList.get(random.nextInt(upper)));

                }
                if (item.getType() == Material.PLAYER_HEAD) {
                    event.setCancelled(true);

                    int upper = CustomWeapon.customWeaponsList.size();
                    Random random = new Random();

                    player.getInventory().addItem(CustomWeapon.customWeaponsList.get(random.nextInt(upper)));

                }
            }

                if (meta.getPersistentDataContainer().has(plugin.Key_NBT_First_Attack)) {

                    String firstSkillKey = meta.getPersistentDataContainer().get(plugin.Key_NBT_First_Attack, PersistentDataType.STRING);

                    ItemStack itemInOff = player.getInventory().getItemInOffHand();

                    if (itemInOff == item) {
                        return;
                    }

                    Block block = event.getPlayer().getTargetBlock(null, 100);
                    if (block.getState() instanceof InventoryHolder invHolder) {
                        Inventory inv = invHolder.getInventory();
                        event.getPlayer().openInventory(inv);
                        return;
                    }

                    if (player.hasCooldown(item.getType())) {
                        event.setCancelled(true);
                        return;
                    }

                    if (firstSkillKey != null) {
                        Map<String, SkillsInterface> skillMap = SkillManager.getSkillMap();

                        if (firstSkillKey.equals("None")) {
                            return;
                        }

                        if (!skillMap.isEmpty()) {
                            for (String skill : skillMap.keySet()) {
                                if (skill != null) {
                                    if (firstSkillKey.equals(skill)) {
                                        event.setCancelled(true);
                                        skillMap.get(skill).useSkill(player, item);
                                        player.setCooldown(item.getType(), (int) skillMap.get(skill).getSkillCooldown()*20);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {

        ItemStack item = event.getItemDrop().getItemStack();
        if (item.getType() == Material.AIR) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Second_Attack)) {

            String secondSkillKey = meta.getPersistentDataContainer().get(plugin.Key_NBT_Second_Attack, PersistentDataType.STRING);
            Player player = event.getPlayer();

            if (CooldownManager.hasCooldown(player.getUniqueId())) {
                event.setCancelled(true);
                return;
            }

            if (secondSkillKey != null) {
                Map<String, SkillsInterface> skillMap = SkillManager.getSkillMap();

                if (secondSkillKey.equals("None")) {
                    event.setCancelled(true);
                    return;
                }

                if (!skillMap.isEmpty()) {
                    for (String skill : skillMap.keySet()) {
                        if (skill != null) {
                            if (secondSkillKey.equals(skill)) {
                                event.setCancelled(true);
                                skillMap.get(skill).useSkill(player, item);
                                CooldownManager.setCooldown(player.getUniqueId(), (int) skillMap.get(skill).getSkillCooldown()* 20L);
                            }
                        }
                    }
                }
            }
        }
    }
}