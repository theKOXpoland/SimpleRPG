package mc.theKOXpoland.SimpleRPG.Commands.ApiCommands.SubCommand.Mobs;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.StringArgument;
import dev.jorel.commandapi.executors.ExecutorType;
import mc.theKOXpoland.SimpleRPG.Managers.Mobs.MobManager;
import org.bukkit.entity.Player;

public class MobCommand extends CommandAPICommand {
    public MobCommand() {
        super("mobs");

        withArguments(new StringArgument("mob").replaceSuggestions(ArgumentSuggestions.strings(MobManager.mobsNames)));

        executes((sender, args) -> {
            Player player = (Player) sender;
            String mob = String.valueOf(args.get(0));

            if (MobManager.customMobMap.containsKey(mob)) {
                MobManager.customMobMap.get(mob).spawnEntity(player.getLocation());
            }
        }, ExecutorType.PLAYER)
                .register();
    }
}