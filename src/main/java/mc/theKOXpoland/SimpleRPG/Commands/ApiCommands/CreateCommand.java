package mc.theKOXpoland.SimpleRPG.Commands.ApiCommands;

import com.destroystokyo.paper.profile.PlayerProfile;
import dev.jorel.commandapi.CommandAPICommand;
import mc.theKOXpoland.SimpleRPG.MainFile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class CreateCommand {

    MainFile plugin;
    public CreateCommand(MainFile plugin) {
        this.plugin = plugin;
    }

    private static final String LINK = "http://textures.minecraft.net/texture/";
    private final Map<String, PlayerProfile> textureCache = new HashMap<>();

    public ItemStack createHead(String texture) {
        PlayerProfile cachedProfile = getProfileForTexture(LINK + texture);
        ItemStack headItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) headItem.getItemMeta();
        meta.setPlayerProfile(cachedProfile);
        headItem.setItemMeta(meta);
        return headItem;
    }

    private PlayerProfile getProfileForTexture(String texture) {
        return textureCache.computeIfAbsent(texture, key -> {
            PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
            PlayerTextures textures = profile.getTextures();
            try {
                textures.setSkin(new URL(texture));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            profile.setTextures(textures);
            return profile;
        });
    }


    public void CreateItems() {

        CommandAPICommand itemSubCommand = new CommandAPICommand("item");
        WeaponCommand weaponSubCommand = new WeaponCommand();
        ArmorCommand armorsSubCommand = new ArmorCommand();
        MobCommand mobCommand = new MobCommand();

        itemSubCommand.withSubcommands(weaponSubCommand, armorsSubCommand);

        CommandAPICommand createCommand = new CommandAPICommand("create")
                .withSubcommand(itemSubCommand)
                .withSubcommands(mobCommand);

        createCommand.register();
                /*.withSubcommand(new CommandAPICommand("chest")
                        .executes((sender, args) -> {
                            if (sender instanceof Player player) {

                                ItemStack chest = new ItemStack(Material.CHEST);
                                ItemMeta meta = chest.getItemMeta();
                                meta.addEnchant(Enchantment.LUCK, 1, false);
                                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                                chest.setItemMeta(meta);

                                player.getInventory().addItem(chest);

                                ItemStack normalchest = new ItemStack(Material.PLAYER_HEAD, 1);
                                SkullMeta skull = (SkullMeta) normalchest.getItemMeta();
                                skull.addEnchant(Enchantment.LUCK, 1, false);
                                skull.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                                normalchest.setItemMeta(skull);


                                player.getInventory().addItem(createHead("cba77856d80dade55699544ddcd685964b0180823fe4ff270de7e5f58aecf3a5"));
                                player.getInventory().addItem(createHead("56d7fdb50f14c731c727b0e0d189b6a874319fc0d79c8a099acfc77c7b2d9196"));
                                player.getInventory().addItem(createHead("8d6a60e5799f4edb1bf52df49474d7b428c964e4a941328e241a1eab96025869"));
                                player.getInventory().addItem(createHead("c285dd77c64e2368fe77c31ff7c3d42b700fb7b74f2b463e696916c90f5d27ab"));
                                player.getInventory().addItem(createHead("940d127a92d0b3d5b5a32add67ddf7db98c1ad7d54919494662e8deaeb4a0d42"));
                                player.getInventory().addItem(createHead("cd592402a294efed3b8b6e59a53c2fbe71eabd85ed05fa6b0b0baa04303c086f"));


                            }
                        })
                )*/
    }
}
