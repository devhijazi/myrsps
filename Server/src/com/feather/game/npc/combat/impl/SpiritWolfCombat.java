package com.feather.game.npc.combat.impl;

import com.feather.game.Animation;
import com.feather.game.Entity;
import com.feather.game.Graphics;
import com.feather.game.World;
import com.feather.game.npc.NPC;
import com.feather.game.npc.combat.CombatScript;
import com.feather.game.npc.combat.NPCCombatDefinitions;
import com.feather.game.npc.familiar.Familiar;
import com.feather.game.player.Player;

public class SpiritWolfCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 6829, 6828 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		Familiar familiar = (Familiar) npc;
		boolean usingSpecial = familiar.hasSpecialOn();
		if (usingSpecial) {// priority over regular attack
			familiar.submitSpecial(familiar.getOwner());
			npc.setNextAnimation(new Animation(8293));
			npc.setNextGraphics(new Graphics(1334));
			World.sendProjectile(npc, target, 1333, 34, 16, 30, 35, 16, 0);
			if (target instanceof NPC) {
				if (!(((NPC) target).getCombatDefinitions().getAttackStyle() == NPCCombatDefinitions.SPECIAL))
					target.setAttackedByDelay(3000);// three seconds
				else
					familiar.getOwner()
							.getPackets()
							.sendGameMessage(
									"Your familiar cannot scare that monster.");
			} else if (target instanceof Player)
				familiar.getOwner()
						.getPackets()
						.sendGameMessage("Your familiar cannot scare a player.");
			else if (target instanceof Familiar)
				familiar.getOwner()
						.getPackets()
						.sendGameMessage(
								"Your familiar cannot scare other familiars.");
		} else {
			npc.setNextAnimation(new Animation(6829));
			delayHit(
					npc,
					1,
					target,
					getMagicHit(
							npc,
							getRandomMaxHit(npc, 40, NPCCombatDefinitions.MAGE,
									target)));
		}
		return defs.getAttackDelay();
	}

}
