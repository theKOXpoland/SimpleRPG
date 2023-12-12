package mc.theKOXpoland.SimpleRPG.Listeners.Items;

import mc.theKOXpoland.SimpleRPG.Utils.CustomChecker;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Managers.Items.CustomItemsManager;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class ItemReplace implements Listener {

    private static MainFile plugin;
    public ItemReplace(MainFile plugin) {
        ItemReplace.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Inventory inventory = player.getInventory();

        if (inventory.isEmpty()) {
            return;
        }

        for (int i = 0; i <= inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null) {
                ItemMeta meta = item.getItemMeta();
                if (item.hasItemMeta()) {
                    if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                        String NBTName = meta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

                        if (NBTName != null) {
                            for (ItemStack customItem : CustomItemsManager.getAllCustomItemsStackList()) {
                                if (customItem != null) {
                                    ItemMeta customMeta = customItem.getItemMeta();

                                    if (customMeta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {

                                        String NBTCustomName = customMeta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

                                        if (NBTCustomName != null) {
                                            if (NBTCustomName.equals(NBTName)) {
                                                if (item == customItem) {
                                                    return;
                                                }
                                                inventory.setItem(i, customItem);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onContainerOpen(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getClickedBlock() == null) {
            return;
        }

        if (event.getClickedBlock().getState() instanceof Container container) {
            Inventory inventory = container.getInventory();

            if (inventory.isEmpty()) {
                return;
            }

            for (int i = 0; i <= inventory.getSize()-1; i++) {
                ItemStack item = inventory.getItem(i);
                if (item != null) {
                    ItemMeta meta = item.getItemMeta();
                    if (item.hasItemMeta()) {
                        if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                            String NBTName = CustomChecker.itemHasName(meta);

                            if (NBTName != null) {
                                for (ItemStack customItem : CustomItemsManager.getAllCustomItemsStackList()) {
                                    if (customItem != null) {
                                        ItemMeta customMeta = customItem.getItemMeta();

                                        if (customMeta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {

                                            String NBTCustomName = customMeta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

                                            if (NBTCustomName != null) {
                                                if (NBTCustomName.equals(NBTName)) {
                                                    if (item == customItem) {
                                                        return;
                                                    }
                                                    inventory.setItem(i, customItem);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Inventory playerInventory = player.getInventory();

            if (playerInventory.isEmpty()) {
                return;
            }

            for (int i = 0; i <= playerInventory.getSize()-1; i++) {
                ItemStack item = playerInventory.getItem(i);
                if (item != null) {
                    ItemMeta meta = item.getItemMeta();
                    if (item.hasItemMeta()) {
                        if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
                            String NBTName = meta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

                            if (NBTName != null) {
                                for (ItemStack customItem : CustomItemsManager.getAllCustomItemsStackList()) {
                                    if (customItem != null) {
                                        ItemMeta customMeta = customItem.getItemMeta();

                                        if (customMeta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {

                                            String NBTCustomName = customMeta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

                                            if (NBTCustomName != null) {
                                                if (NBTCustomName.equals(NBTName)) {
                                                    if (item == customItem) {
                                                        return;
                                                    }
                                                    playerInventory.setItem(i, customItem);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
