package com.feather.game.npc.combat.impl;

import com.feather.game.Animation;
import com.feather.game.Entity;
import com.feather.game.Graphics;
import com.feather.game.World;
import com.feather.game.npc.NPC;
import com.feather.game.npc.combat.CombatScript;
import com.feather.game.npc.combat.NPCCombatDefinitions;
import com.feather.game.player.Player;
import com.feather.game.player.Skills;
import com.feather.utils.Utils;

public class KarilCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] {2028};
	}

	@Override
	public int attack(NPC npc, Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		npc.setNextAnimation(new Animation(defs.getAttackEmote()));
		int damage = getRandomMaxHit(npc, defs.getMaxHit(), NPCCombatDefinitions.RANGE, target);
		if(damage != 0 && target instanceof Player && Utils.random(3) == 0) {	
			target.setNextGraphics(new Graphics(401, 0, 100));
			Player targetPlayer = (Player) target;
			int drain = (int) (targetPlayer.getSkills().getLevelForXp(Skills.AGILITY) * 0.2);
			int currentLevel = targetPlayer.getSkills().getLevel(Skills.AGILITY);
			targetPlayer.getSkills().set(Skills.AGILITY, currentLevel < drain ? 0 : currentLevel-drain);
		}
		World.sendProjectile(npc, target, defs.getAttackProjectile(), 41, 16, 41, 35, 16, 0);
		delayHit(npc, 2, target, getRangeHit(npc, damage));
		return defs.getAttackDelay();
	}
}
