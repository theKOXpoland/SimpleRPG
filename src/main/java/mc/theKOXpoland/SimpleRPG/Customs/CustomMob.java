package mc.theKOXpoland.SimpleRPG.Customs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class CustomMob {

    private static MainFile plugin;
    public CustomMob(MainFile plugin) {
        CustomMob.plugin = plugin;
    }

    private EntityType entityType;
    private String customName;
    private String customType;
    private String name;
    private double health;
    private int droppedExp;
    private boolean customNameVisible;
    private boolean burnInDay;
    private boolean AI;
    private boolean isBaby;
    private Map<String, ItemStack> mobEquipement;
    private List<String> mobDrop;

    public void spawnEntity(Location location) {
        Entity entity = location.getWorld().spawnEntity(location, entityType);

        if (customNameVisible) {
           entity.customName(Util.mm(customName));
        }

        NamespacedKey KeyNBTName = plugin.Key_NBT_Name;
        NamespacedKey KeyNBTtype = plugin.Key_NBT_Type;

        entity.getPersistentDataContainer().set(KeyNBTName, PersistentDataType.STRING , name);
        entity.getPersistentDataContainer().set(KeyNBTtype, PersistentDataType.STRING , name);

        if (entity instanceof Damageable damageable) {
            damageable.setHealth(health);
        }

        if (entity instanceof Zombie zombie) {
            zombie.setShouldBurnInDay(burnInDay);

            if (isBaby) {
                zombie.setBaby();
            }
            if (!isBaby) {
                zombie.isAdult();
            }

            EntityEquipment equipment = zombie.getEquipment();
            equipment.setHelmet(mobEquipement.get("Helmet"));
            equipment.setChestplate(mobEquipement.get("Chestplate"));
            equipment.setLeggings(mobEquipement.get("Leggings"));
            equipment.setBoots(mobEquipement.get("Boots"));
            equipment.setItemInMainHand(mobEquipement.get("MainHand"));
            equipment.setItemInOffHand(mobEquipement.get("OffHand"));
        }

        if (entity instanceof LivingEntity livingEntity) {
            livingEntity.setAI(AI);
        }
    }
}