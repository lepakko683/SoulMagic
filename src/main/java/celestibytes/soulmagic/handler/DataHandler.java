package celestibytes.soulmagic.handler;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import celestibytes.soulmagic.datatypes.PlayerExtraData;
import celestibytes.soulmagic.misc.Log;

public class DataHandler {
	public static HashMap<UUID, PlayerExtraData> playerData = new HashMap<UUID, PlayerExtraData>();
	
	public static void clearData() {
		if(!playerData.isEmpty()) {
			playerData.clear();
		}
	}
	
	public static PlayerExtraData getExtraData(EntityPlayer player) {
		if(player != null) {
			PlayerExtraData data = playerData.get(player.getUniqueID());
			if(data != null) {
				return data;
			}
			
			data = PlayerExtraData.createNew(player);
			playerData.put(player.getUniqueID(), data);
			
			return data;
		}
		return null;
		
	}
	
//	public static PlayerExtraData getExtraDataNonNull(EntityPlayer player) {
//		if(player != null) {
//			
//		}
//		return player != null ? playerData.get(player.getUniqueID()) : null;
//	}
	
	public static boolean setExtraData(PlayerExtraData data, EntityPlayer player) {
		if(player == null) {
			return false;
		}
		
		Log.debug("Data for player " + player.getDisplayName() + " set");
		playerData.put(player.getUniqueID(), data);
			
		return true;
	}
	
	// TODO implement this once there is something to sync
	public static void setAndSyncExtraData(PlayerExtraData data, EntityPlayerMP player) {
		setExtraData(data, player);
//		player.get
	}
	
	public static boolean initPlayerDataIfNeeded(EntityPlayer player) {
		return setExtraData(PlayerExtraData.createNew(player), player);
	}
}
