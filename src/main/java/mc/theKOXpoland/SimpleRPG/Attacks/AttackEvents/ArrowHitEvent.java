package mc.theKOXpoland.SimpleRPG.Attacks.AttackEvents;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ArrowHitEvent extends Event {
    private final Player shooter;
    private final Entity target;
    private double damage;

    public ArrowHitEvent(Player shooter, Entity target, double damage) {
        this.shooter = shooter;
        this.target = target;
        this.damage = damage;
    }

    public Player getShooter() {
        return shooter;
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

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
