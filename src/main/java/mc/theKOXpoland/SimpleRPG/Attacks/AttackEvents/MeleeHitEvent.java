package mc.theKOXpoland.SimpleRPG.Attacks.AttackEvents;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MeleeHitEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final Entity target;
    private double damage;

    public MeleeHitEvent(Player player, Entity entity, double damage) {
        this.player = player;
        this.target = entity;
        this.damage = damage;
    }

    public Player getPlayer() {
        return player;
    }

    public Entity getTarget() {
        return target;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static @NotNull HandlerList getHandlerList() {
        return handlers;
    }
}
