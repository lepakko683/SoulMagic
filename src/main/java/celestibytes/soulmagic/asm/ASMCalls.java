package celestibytes.soulmagic.asm;

import celestibytes.soulmagic.api.CurseHelper;
import celestibytes.soulmagic.content.curses.CurseGluttony;
import celestibytes.soulmagic.init.ModCurses;
import net.minecraft.entity.player.EntityPlayer;

public class ASMCalls {
	
	public static float getExhaustion(float baseExhaustion, EntityPlayer plr) {
		if(CurseHelper.hasPlayerCurse(ModCurses.CURSE_GLUTTONY, plr)) {
			return baseExhaustion * CurseGluttony.exhaustionMultiplier;
		}
		
		
		return baseExhaustion;
	}
}
