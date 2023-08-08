package mc.theKOXpoland.SimpleRPG.Tasks;

import mc.theKOXpoland.SimpleRPG.Items.Customs.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Others.Customs.CustomChecker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

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

        CustomWeapon customWeapon = CustomChecker.getCustomWeapon(item);

        if (customWeapon == null) {
            return;
        }

        String attackSpeed = customWeapon.getAttackSpeed();

        switch (attackSpeed) {
            case "Fast" ->
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 5, -1, false, false, false));
            case "Slow" ->
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 5, 1, false, false, false));
            case "VerySlow" ->
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 5, 5, false, false, false));
            case "UltraSlow" ->
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 5, 10, false, false, false));
            default -> player.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        }

    }
}