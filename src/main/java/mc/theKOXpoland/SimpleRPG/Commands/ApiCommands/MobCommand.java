package mc.theKOXpoland.SimpleRPG.Commands.ApiCommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.ExecutorType;
import mc.theKOXpoland.SimpleRPG.Managers.MobsManager;
import org.bukkit.entity.Player;

public class MobCommand extends CommandAPICommand {
    public MobCommand() {
        super("mobs");

        withArguments(new StringArgument("mob").replaceSuggestions(ArgumentSuggestions.strings(MobsManager.mobsNames)));

        executes((sender, args) -> {
            Player player = (Player) sender;
            String mob = args.get(0).toString();

            if (MobsManager.customMobMap.containsKey(mob)) {
                MobsManager.customMobMap.get(mob).spawnEntity(player.getLocation());
            }
        }, ExecutorType.PLAYER)
                .register();
    }
}