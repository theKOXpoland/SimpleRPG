package mc.theKOXpoland.SimpleRPG.Items.Listeners;

import mc.theKOXpoland.SimpleRPG.MainFile;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerInteractionEvent implements Listener {

    MainFile plugin;
    public PlayerInteractionEvent(MainFile plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onAttackInteraction(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_BLOCK) {
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
                    event.setCancelled(true);
                }
            }
        }
    }
}
