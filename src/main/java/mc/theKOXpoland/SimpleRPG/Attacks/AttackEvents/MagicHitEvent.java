package mc.theKOXpoland.SimpleRPG.Attacks.AttackEvents;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MagicHitEvent extends Event {

    private final Player player;
    private final Entity target;
    private double damage;
    private final boolean isParticleAttack;

    public MagicHitEvent(Player player, Entity entity, double damage, boolean isParticleAttack) {
        this.player = player;
        this.target = entity;
        this.damage = damage;
        this.isParticleAttack = isParticleAttack;
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

    public boolean isParticleAttack() {
        return isParticleAttack;
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
