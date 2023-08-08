package mc.theKOXpoland.SimpleRPG.Items.Customs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

@AllArgsConstructor
@Getter
public class CustomItem {

    private static MainFile plugin;
    public CustomItem(MainFile plugin) {
        CustomItem.plugin = plugin;
    }

    private Material itemMaterialType;
    private String itemName;
    private String displayName;
    private int itemAmount;
    private int customModelData;
    private boolean unbreakable;
    private boolean breakBlocks;
    private String enchants;
    private List<Component> lore;

    public static List<ItemStack> customItemList = new ArrayList<>();
    public static List<String> itemsName = new ArrayList<>();
    public static final Map<String, ItemStack> customItemMap = new HashMap<>();

    public CustomItem createItem() {
        ItemStack Item = new ItemStack(itemMaterialType, itemAmount);
        ItemMeta meta = Item.getItemMeta();

        if (enchants.isEmpty() || enchants.equals("None")) {
            meta.setUnbreakable(true);
        } else {
            String enchantment = enchants;

            String[] splitedEnchants = enchantment.split(",");
            for (String ench : splitedEnchants) {
                try {
                    String[] splitted = ench.split(":");
                    if (Enchantment.getByKey(NamespacedKey.minecraft(splitted[0].toLowerCase())) != null) {
                        meta.addEnchant(Objects.requireNonNull(Enchantment.getByKey(NamespacedKey.minecraft(splitted[0].toLowerCase()))),
                                Integer.parseInt(splitted[1]), false);
                    }
                    meta.setUnbreakable(true);
                } catch (IllegalArgumentException exp) {
                    plugin.getLogger().severe(ench + " is wrong!");
                    meta.setUnbreakable(true);
                }
            }
        }

        NamespacedKey KeyNBTName = plugin.Key_NBT_Name;

        meta.getPersistentDataContainer().set(KeyNBTName, PersistentDataType.STRING , itemName);

        meta.displayName(Util.mm(displayName));

        if (customModelData != 0) {
            meta.setCustomModelData(customModelData);
        }

        meta.setUnbreakable(unbreakable);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

        meta.lore(lore);

        Item.setItemMeta(meta);

        customItemList.add(Item);
        itemsName.add(itemName);
        customItemMap.put(itemName, Item);

        return this;
    }
}
