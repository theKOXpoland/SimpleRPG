package mc.theKOXpoland.SimpleRPG.Listeners;

import mc.theKOXpoland.SimpleRPG.Customs.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Managers.CooldownManager;
import mc.theKOXpoland.SimpleRPG.Skills.Dash;
import mc.theKOXpoland.SimpleRPG.Tasks.CooldownTask;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
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
                        switch (firstSkillKey) {
                            case "test1":
                                event.setCancelled(true);
                                Dash.dash(player);
                                player.setCooldown(item.getType(), 100);
                                break;
                            case "test2":
                                // code block
                                break;
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
                switch (secondSkillKey) {
                    case "test2":
                        event.setCancelled(true);
                        player.sendMessage(Util.fix("&a&lŁo chuj!"));
                        CooldownManager.setCooldown(player.getUniqueId(), 10.0);
                        new CooldownTask(plugin).runTaskTimer(plugin, 0, 20);
                        break;
                    case "test1":
                        // code block
                        break;
                    default:
                        // code block
                }
            }
        }
    }
}