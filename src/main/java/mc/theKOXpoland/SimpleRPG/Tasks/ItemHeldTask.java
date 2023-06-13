package mc.theKOXpoland.SimpleRPG.Tasks;

import mc.theKOXpoland.SimpleRPG.MainFile;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ItemHeldTask extends BukkitRunnable {

    MainFile plugin;

    public ItemHeldTask(MainFile plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            setAttackCooldown(player);
        }
    }

    public void setAttackCooldown(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }

        List<Component> lore = meta.lore();
        if (lore == null || lore.isEmpty()) {
            return;
        }

        if (meta.getPersistentDataContainer().has(plugin.Key_NBT_First_Attack)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 5, 0, false, false, false));
        }
        if (meta.getPersistentDataContainer().has(plugin.Key_NBT_Second_Attack)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 5, 6, false, false, false));
        }
        if (!meta.getPersistentDataContainer().has(plugin.Key_NBT_First_Attack) || !meta.getPersistentDataContainer().has(plugin.Key_NBT_Second_Attack)) {
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        }

        /*String SzybkiAtak = "§7Prędkość: §fSzybki";
        String PowolnyAtak = "§7Prędkość: §8Powolny";

        if (lore.contains(SzybkiAtak)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 5, 0, false, false, false));
        } else
        if (lore.contains(PowolnyAtak)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 5, 6, false, false, false));
        } else {
            player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        }*/

    }
}