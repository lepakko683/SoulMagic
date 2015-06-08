package celestibytes.soulmagic.api;

import net.minecraft.entity.player.EntityPlayer;

public class CurseHelper {
	
	public static boolean willCurseSucceed(EntityPlayer caster, EntityPlayer target) {
		return true; // TODO: check target's NBT for curse protection
	}
	
	public static boolean hasPlayerCurseProtect(EntityPlayer player) {
		return false;
	}
	
}
