package celestibytes.soulmagic.handler;

import java.io.File;

import celestibytes.soulmagic.datatypes.PlayerExtraData;
import celestibytes.soulmagic.misc.Log;
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
			EntityPlayerMP plr = (EntityPlayerMP) e.entityPlayer;
			
			File save = getSaveFile(plr, e.playerDirectory, false);
			File saveBack = getSaveFile(plr, e.playerDirectory, true);
			PlayerExtraData data = PlayerExtraData.readFromFile(save, saveBack, plr);
			if(data != null) {
				Log.debug("Data for player " + plr.getDisplayName() + " loaded.");
				DataHandler.setExtraData(data, plr);
			}
//			DataHandler.setExtraData(PlayerExtraData.createNew(e.entityPlayer), e.entityPlayer);
		} else {
			Log.warn("Player not instance of EntityPlayerMP o.O");
//			DataHandler.setExtraData(PlayerExtraData.createNew(e.entityPlayer), e.entityPlayer);
		}
	}
	
	@SubscribeEvent
	public void onSaveToFile(PlayerEvent.SaveToFile e) {
		if(e.entityPlayer instanceof EntityPlayerMP) {
			EntityPlayerMP plr = (EntityPlayerMP) e.entityPlayer;
			PlayerExtraData data = DataHandler.getExtraData(e.entityPlayer);
			if(data != null) {
				data.writeToFile(getSaveFile(plr, e.playerDirectory, false), getSaveFile(plr, e.playerDirectory, true));
			} else {
				Log.err("Player " + e.entityPlayer.getDisplayName() + " didn't have extra data, this shouldn't happen!");
			}
		} else {
			Log.warn("Player not instance of EntityPlayerMP o.O");
		}
	}
	
	private File getSaveFile(EntityPlayerMP player, File dir, boolean backup) {
		if(backup) {
			return new File(dir, player.getDisplayName() + "." + SUFFIX + ".back");
		}
		return new File(dir, player.getDisplayName() + "." + SUFFIX);
	}
}
