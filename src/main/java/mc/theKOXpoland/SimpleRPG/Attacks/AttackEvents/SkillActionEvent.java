package mc.theKOXpoland.SimpleRPG.Attacks.AttackEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SkillActionEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;

    public SkillActionEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }
}