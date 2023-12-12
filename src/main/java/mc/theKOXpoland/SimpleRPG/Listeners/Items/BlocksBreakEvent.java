package mc.theKOXpoland.SimpleRPG.Listeners.Items;

import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomItem;
import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Managers.Items.CustomItemsManager;
import mc.theKOXpoland.SimpleRPG.Managers.Items.ItemManager;
import mc.theKOXpoland.SimpleRPG.Managers.Items.WeaponManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class BlocksBreakEvent implements Listener {

    private static MainFile plugin;
    public BlocksBreakEvent(MainFile plugin) {
        BlocksBreakEvent.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        ItemStack item = event.getPlayer().getActiveItem();

        if (item.getType() == Material.AIR) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Name)) {
            String NBTName = meta.getPersistentDataContainer().get(plugin.Key_NBT_Name, PersistentDataType.STRING);

            if (NBTName != null) {
                for (String customKey : CustomItemsManager.getAllCustomItemsNamesList()) {
                    if (customKey.equals(NBTName)) {
                        CustomWeapon customWeapon = null;
                        CustomItem customItem = null;

                        try {
                            customWeapon = WeaponManager.customWeaponMap.get(customKey);
                        } catch (IllegalArgumentException exp) {
                            customItem = ItemManager.customItemMap.get(customKey);
                        }

                        if (customWeapon != null) {
                            if (customWeapon.isBreakBlocks()) {
                                break;
                            }
                            event.setCancelled(true);
                            break;
                        }

                        if (customItem != null) {
                            if (customItem.isBreakBlocks()) {
                                break;
                            }
                            event.setCancelled(true);
                            break;
                        }

                        break;
                    }
                }
            }
        }
    }
}
