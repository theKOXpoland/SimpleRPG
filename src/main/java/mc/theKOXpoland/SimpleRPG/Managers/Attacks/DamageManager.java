package mc.theKOXpoland.SimpleRPG.Managers.Attacks;

import mc.theKOXpoland.SimpleRPG.Constructors.Items.CustomWeapon;
import mc.theKOXpoland.SimpleRPG.Utils.CustomChecker;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class DamageManager {

    public static double calculateMeleeDamage(ItemStack item, double damage) {

        if (item == null || item.getType() == Material.AIR) {
            return damage;
        }

        CustomWeapon customWeapon = CustomChecker.getCustomWeapon(item);

        if (customWeapon != null) {
            return customWeapon.getDamage();
        }

        return damage;
    }

    public static double calculateMagicDamage(ItemStack item) {

        if (item == null || item.getType() == Material.AIR) {
            return 1;
        }

        CustomWeapon customWeapon = CustomChecker.getCustomWeapon(item);

        if (customWeapon != null) {
            return customWeapon.getDamage();
        }

        return 1;
    }

    public static double calculateArrowDamage(ItemStack item) {

        if (item == null || item.getType() == Material.AIR) {
            return 1;
        }

        CustomWeapon customWeapon = CustomChecker.getCustomWeapon(item);

        if (customWeapon != null) {
            return customWeapon.getDamage();
        }

        return 1;
    }

}
