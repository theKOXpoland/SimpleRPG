package mc.theKOXpoland.SimpleRPG.Items.Listeners;

import mc.theKOXpoland.SimpleRPG.MainFile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemDepravation implements Listener {

    private final MainFile plugin;
    public ItemDepravation(MainFile plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event) {

        if (event.getClick() == ClickType.NUMBER_KEY || event.getAction() == InventoryAction.HOTBAR_SWAP) {
            event.setCancelled(true);
            return;
        }

        ItemStack item = event.getCurrentItem();

        if (item == null) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return;
        }

        if (event.getInventory().getType() == InventoryType.WORKBENCH) {

            if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                event.setCancelled(true);
            }
            if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                    event.setCancelled(true);
                }
            }
            return;
        }

        if (event.getInventory().getType() == InventoryType.PLAYER) {

            if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                event.setCancelled(true);
            }
            if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                    event.setCancelled(true);
                }
            }
            return;
        }

        if (event.getInventory().getType() == InventoryType.ANVIL) {

            if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                event.setCancelled(true);
            }
            if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                    event.setCancelled(true);
                }
            }
            return;
        }

        if (event.getInventory().getType() == InventoryType.GRINDSTONE) {
            if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                event.setCancelled(true);
            }
            if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                    event.setCancelled(true);
                }
            }
            return;
        }

        if (event.getInventory().getType() == InventoryType.FURNACE || event.getInventory().getType() == InventoryType.BLAST_FURNACE) {
            if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                event.setCancelled(true);
            }
            if (event.getSlotType() == InventoryType.SlotType.RESULT) {
                if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                    event.setCancelled(true);
                }
            }
        }

    }
}