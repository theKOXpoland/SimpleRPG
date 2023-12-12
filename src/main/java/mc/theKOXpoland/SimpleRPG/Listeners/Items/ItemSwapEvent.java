package mc.theKOXpoland.SimpleRPG.Listeners.Items;

import mc.theKOXpoland.SimpleRPG.MainFile;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemSwapEvent implements Listener {

    private final MainFile plugin;
    public ItemSwapEvent(MainFile plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void inMainHand(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        ItemMeta meta = item.getItemMeta();

        if (meta == null || item.getType() == Material.AIR) {
            return;
        }

        if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void inOffHand(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInOffHand();
        Inventory inv = player.getInventory();

        if (item.getType() == Material.AIR) {
            return;
        }

            if (item.hasItemMeta()) {
                ItemMeta meta = item.getItemMeta();
                if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {

                    if (inv.firstEmpty() == -1) {
                        player.getInventory().setItemInOffHand(null);
                        player.getWorld().dropItem(player.getLocation().add(0, 1, 0), item);
                    } else {
                        player.getInventory().setItemInOffHand(null);
                        inv.addItem(item);
                    }
                }
            }
        }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInOffHand();
        Inventory inv = player.getInventory();

        if (item.getType() == Material.AIR) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            if (inv.firstEmpty() == -1) {
                event.setCancelled(true);
                player.getInventory().setItemInOffHand(null);
                player.getWorld().dropItem(player.getLocation().add(0, 1, 0), item);
            } else {
                event.setCancelled(true);
                player.getInventory().setItemInOffHand(null);
                inv.addItem(item);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        HumanEntity player = event.getPlayer();
        Inventory inv = player.getInventory();
        ItemStack item = player.getInventory().getItemInOffHand();

        if (item.getType() == Material.AIR) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            if (inv.firstEmpty() == -1) {
                player.getInventory().setItemInOffHand(null);
                player.getWorld().dropItem(player.getLocation().add(0, 1, 0), item);
            } else {
                player.getInventory().setItemInOffHand(null);
                inv.addItem(item);
            }
        }
    }

}
