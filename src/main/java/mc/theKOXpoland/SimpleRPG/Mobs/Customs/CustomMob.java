package mc.theKOXpoland.SimpleRPG.Mobs.Customs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.Util;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.goals.WanderGoal;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.mcmonkey.sentinel.SentinelIntegration;
import org.mcmonkey.sentinel.SentinelTrait;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class CustomMob extends SentinelIntegration {

    private static MainFile plugin;
    public CustomMob(MainFile plugin) {
        CustomMob.plugin = plugin;
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
        Entity entity = location.getWorld().spawnEntity(location, entityType);

        if (customNameVisible) {
           entity.customName(Util.mm(customName));
        }

        NamespacedKey KeyNBTName = plugin.Key_NBT_Name;
        NamespacedKey KeyNBTtype = plugin.Key_NBT_Type;

        entity.getPersistentDataContainer().set(KeyNBTName, PersistentDataType.STRING, name);
        entity.getPersistentDataContainer().set(KeyNBTtype, PersistentDataType.STRING, customType);

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
            livingEntity.setAI(AI);
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

    public void spawnCitizensNPC(Location location) {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC(entityType, customName);
        npc.spawn(location);

        npc.data().setPersistent(NPC.Metadata.NAMEPLATE_VISIBLE, customNameVisible);
        npc.data().setPersistent(NPC.Metadata.PICKUP_ITEMS, false);
        npc.data().setPersistent(NPC.Metadata.VILLAGER_BLOCK_TRADES, true);
        npc.data().setPersistent(NPC.Metadata.USE_MINECRAFT_AI, false);
        npc.data().setPersistent(NPC.Metadata.DEFAULT_PROTECTED, false);
        npc.data().setPersistent(NPC.Metadata.SWIMMING, true);

        npc.getDefaultGoalController().addGoal(WanderGoal.builder(npc).xrange(3).yrange(3).build(), 3);
        npc.getDefaultGoalController().addGoal(WanderGoal.builder(npc).fallback(npc1 -> location).build(), 2);

        if (npc instanceof Entity entity) {
            NamespacedKey KeyNBTName = plugin.Key_NBT_Name;
            NamespacedKey KeyNBTtype = plugin.Key_NBT_Type;

            entity.getPersistentDataContainer().set(KeyNBTName, PersistentDataType.STRING, name);
            entity.getPersistentDataContainer().set(KeyNBTtype, PersistentDataType.STRING, customType);
        }

        SentinelTrait sentinel = npc.getOrAddTrait(SentinelTrait.class);

        sentinel.allowKnockback = true;
        sentinel.healRate = 0;
        sentinel.respawnTime = -1;
        sentinel.chaseRange = 2;
        sentinel.speed = speed;
        sentinel.damage = damage;
        sentinel.realistic = true;

        if (customType.equals("Hostile")) {
            npc.data().setPersistent(NPC.Metadata.AGGRESSIVE, true);
            npc.data().setPersistent(NPC.Metadata.TARGETABLE, false);
            sentinel.closeChase = true;
            if (shouldDamage) {
                npc.data().setPersistent(NPC.Metadata.DAMAGE_OTHERS, true);
                npc.getNavigator().getLocalParameters().attackRange(2.0).attackDelayTicks(20);
            }
        }
        if (customType.equals("Neutral")) {
            npc.data().setPersistent(NPC.Metadata.AGGRESSIVE, false);
            npc.data().setPersistent(NPC.Metadata.TARGETABLE, false);
            if (shouldDamage) {
                npc.data().setPersistent(NPC.Metadata.DAMAGE_OTHERS, true);
                npc.getNavigator().getLocalParameters().attackRange(2.0).attackDelayTicks(20);
                sentinel.fightback = true;
            }
        }
        if (customType.equals("Friendly")) {
            npc.data().setPersistent(NPC.Metadata.AGGRESSIVE, false);
            npc.data().setPersistent(NPC.Metadata.TARGETABLE, true);
            sentinel.fightback = false;
            sentinel.runaway = true;

            if (shouldDamage) {
                npc.data().setPersistent(NPC.Metadata.DAMAGE_OTHERS, false);
            }
        }
        if (isSilent) {
            npc.data().setPersistent(NPC.Metadata.SILENT, false);
        }


        if (entityType == EntityType.VILLAGER && npc.getEntity() instanceof Villager villager) {
            villager.setProfession(Villager.Profession.NONE);
        }

        Equipment equipmentTrait = npc.getOrAddTrait(Equipment.class);

        equipmentTrait.set(Equipment.EquipmentSlot.HELMET, mobEquipement.get("Helmet"));
        equipmentTrait.set(Equipment.EquipmentSlot.CHESTPLATE, mobEquipement.get("Chestplate"));
        equipmentTrait.set(Equipment.EquipmentSlot.LEGGINGS, mobEquipement.get("Leggings"));
        equipmentTrait.set(Equipment.EquipmentSlot.BOOTS, mobEquipement.get("Boots"));
        equipmentTrait.set(Equipment.EquipmentSlot.HAND, mobEquipement.get("MainHand"));
        equipmentTrait.set(Equipment.EquipmentSlot.OFF_HAND, mobEquipement.get("OffHand"));

        if (npc.getEntity() instanceof LivingEntity livingEntity) {
            livingEntity.setAI(AI);
            livingEntity.setGlowing(isGlowing);
            livingEntity.setSilent(isSilent);
            livingEntity.setCanPickupItems(false);
        }
    }
}