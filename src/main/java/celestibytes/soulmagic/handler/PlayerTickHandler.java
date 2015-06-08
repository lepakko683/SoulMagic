package celestibytes.soulmagic.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class PlayerTickHandler {
	
	public void onPlayerUpdate(PlayerEvent.LivingUpdateEvent e) {
		if(e.entity instanceof EntityPlayer) {
			EntityPlayer plr = (EntityPlayer) e.entity;
			plr.getUniqueID()
		}
	}
}
