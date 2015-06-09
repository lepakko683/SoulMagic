package celestibytes.soulmagic.handler;

import java.io.File;

import celestibytes.soulmagic.datatypes.PlayerExtraData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraftforge.event.entity.player.PlayerEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class PlayerEventHandler {
	
	public static final String SUFFIX = "soulm";
	
	public void onPlayerUpdate(PlayerEvent.LivingUpdateEvent e) {
		
	}
	
	@SubscribeEvent
	public void onLoadFromFile(PlayerEvent.LoadFromFile e) {
		if(e.entityPlayer instanceof EntityPlayerMP) {
			EntityPlayerMP plr = (EntityPlayerMP) e.entity;
			
			File save = getSaveFile(plr, e.playerDirectory, false);
			File saveBack = getSaveFile(plr, e.playerDirectory, true);
			PlayerExtraData data = PlayerExtraData.readFromFile(save, saveBack, plr);
			if(data != null) {
				//DataHandler
			}
			DataHandler.setExtraData(PlayerExtraData.createNew(e.entityPlayer), e.entityPlayer);
		} else {
			DataHandler.setExtraData(PlayerExtraData.createNew(e.entityPlayer), e.entityPlayer);
		}
	}
	
	private File getSaveFile(EntityPlayerMP player, File dir, boolean backup) {
		if(backup) {
			return new File(dir, player.getDisplayName() + "." + SUFFIX + ".back");
		}
		return new File(dir, player.getDisplayName() + "." + SUFFIX);
	}
}
