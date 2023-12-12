package mc.theKOXpoland.SimpleRPG.Constructors.Mobs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CustomMob {

    private static MainFile plugin;
    public CustomMob(MainFile plugin) {
        CustomMob.plugin = plugin;
    }

    private static Location spawnLocation;
    private static Entity entity;

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    private UUID mobUUID;
    private EntityType entityType;
    private String customName;
    private String customType;
    private String name;
    private String customEntityType;
    private double health;
    private double speed;
    private double damage;
    private int droppedExp;
    private boolean customNameVisible;
    private boolean burnInDay;
    private boolean AI;
    private boolean isBaby;
    private boolean isGlowing;
    private boolean respNaturally;
    private boolean isSilent;
    private boolean shouldTarget;
    private boolean shouldDamage;
    private Map<String, ItemStack> mobEquipement;
    private List<String> mobDrop;
    private List<String> respInstead;
    private List<String> biomeResp;
    private List<String> worldResp;

    public void spawnEntity(Location location) {
        entity = location.getWorld().spawnEntity(location, entityType);

        spawnLocation = location;

        if (customNameVisible) {
           entity.customName(Util.mm(customName));
        }

        NamespacedKey KeyNBTName = plugin.Key_NBT_Name;
        NamespacedKey KeyNBTtype = plugin.Key_NBT_Type;

        entity.getPersistentDataContainer().set(KeyNBTName, PersistentDataType.STRING, name);
        entity.getPersistentDataContainer().set(KeyNBTtype, PersistentDataType.STRING, customType);

        if (entity instanceof Mob mob) {
            Bukkit.getMobGoals().removeAllGoals(mob);
        }

        if (entity instanceof Damageable damageable) {
            damageable.setHealth(health);
        }

        if (entity instanceof Ageable ageable) {
            if (isBaby) {
                ageable.setBaby();
            } else {
                ageable.setAdult();
            }
        }

        if (entity instanceof Breedable breedable) {
            breedable.getAgeLock();
        }

        if (entityType.equals(EntityType.ZOMBIE)) {
            if (entity instanceof Zombie zombie) {
                zombie.setShouldBurnInDay(burnInDay);
            }
        }

        if (entityType.equals(EntityType.SKELETON)) {
            if (entity instanceof Skeleton skeleton) {
                skeleton.setShouldBurnInDay(burnInDay);
            }
        }

        if (entityType.equals(EntityType.PHANTOM)) {
            if (entity instanceof Phantom phantom) {
                phantom.setShouldBurnInDay(burnInDay);
            }
        }

        if (entityType.equals(EntityType.VILLAGER)) {
            if (entity instanceof Villager villager) {
                villager.setProfession(Villager.Profession.NONE);
            }
        }

        if (entity instanceof LivingEntity livingEntity) {
            //livingEntity.setAI(AI);
            livingEntity.setGlowing(isGlowing);
            livingEntity.setSilent(isSilent);
            livingEntity.setCanPickupItems(false);

            EntityEquipment equipment = livingEntity.getEquipment();
            if (equipment != null) {
                equipment.setHelmet(mobEquipement.get("Helmet"));
                equipment.setChestplate(mobEquipement.get("Chestplate"));
                equipment.setLeggings(mobEquipement.get("Leggings"));
                equipment.setBoots(mobEquipement.get("Boots"));
                equipment.setItemInMainHand(mobEquipement.get("MainHand"));
                equipment.setItemInOffHand(mobEquipement.get("OffHand"));
            }
        }
    }

    public void initPathfinder() {

    }

    public Entity getAsEntity(CustomMob mob) {
        if (mob != null) {
            System.out.println(entity + " this is entity");
            return entity;
        }
        return null;
    }
}