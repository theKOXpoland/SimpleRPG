package mc.theKOXpoland.SimpleRPG.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import mc.theKOXpoland.SimpleRPG.MainFile;
import mc.theKOXpoland.SimpleRPG.Utils.Util;

public class ConfigCommand {

    MainFile plugin;
    public ConfigCommand(MainFile plugin) {
       this.plugin = plugin;
    }

    public void ConfigReload() {
        new CommandAPICommand("conf")
                .withPermission(CommandPermission.OP)
                .executes((sender, args) -> {

                    plugin.reloadConfig();
                    plugin.configManager.reloadConfig();

                    plugin.saveDefaultConfig();
                    plugin.configManager.saveDefaultConfig();

                    plugin.weaponManager.init();
                    plugin.mobManager.init();
                    plugin.armorManager.init();

                    sender.sendMessage(Util.mm("Reloaded"));
                })
                .register();
    }
}
