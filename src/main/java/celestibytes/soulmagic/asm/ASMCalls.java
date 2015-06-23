package celestibytes.soulmagic.asm;

import net.minecraft.entity.player.EntityPlayer;

public class ASMCalls {
	
	public static float getExhaustion(float baseExhaustion, EntityPlayer plr) {
		return baseExhaustion * 10f;
	}
}
