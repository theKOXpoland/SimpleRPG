package mc.theKOXpoland.SimpleRPG.Managers.Attacks;

import mc.theKOXpoland.SimpleRPG.Attacks.Skills.MainAttackReplace.SkillArrow;
import mc.theKOXpoland.SimpleRPG.Attacks.Skills.Skill.SkillDash;
import mc.theKOXpoland.SimpleRPG.Attacks.Skills.SkillsInterface;
import mc.theKOXpoland.SimpleRPG.Attacks.Skills.MainAttackReplace.SkillSpark;
import mc.theKOXpoland.SimpleRPG.Attacks.Skills.Skill.SkillTeleport;
import mc.theKOXpoland.SimpleRPG.MainFile;

import java.util.HashMap;
import java.util.Map;

public class SkillManager {

    MainFile plugin;
    public SkillManager(MainFile plugin) {
        this.plugin = plugin;
    }

    private static final Map<String, SkillsInterface> skillMap = new HashMap<>();

    public void loadSkills() {
        skillMap.put("Dash", new SkillDash(plugin));
        skillMap.put("Spark", new SkillSpark(plugin));
        skillMap.put("Teleport", new SkillTeleport(plugin));
        skillMap.put("Arrow", new SkillArrow(plugin));
    }

    public static Map<String, SkillsInterface> getSkillMap() {
        return skillMap;
    }
}