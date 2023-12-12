package mc.theKOXpoland.SimpleRPG.AI.CustomTypes;

import com.destroystokyo.paper.entity.Pathfinder;
import mc.theKOXpoland.SimpleRPG.Constructors.Mobs.CustomMob;
import mc.theKOXpoland.SimpleRPG.MainFile;

public abstract class AggressiveAI implements Pathfinder {

    private static MainFile plugin;
    public AggressiveAI(MainFile plugin) {
        AggressiveAI.plugin = plugin;
    }

    private CustomMob customMob;

    public AggressiveAI(CustomMob customMob) {
        this.customMob = customMob;
    }

}