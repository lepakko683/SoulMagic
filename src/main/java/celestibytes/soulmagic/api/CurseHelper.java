package celestibytes.soulmagic.api;

import java.util.Iterator;

import celestibytes.soulmagic.datatypes.PlayerExtraData;
import celestibytes.soulmagic.handler.DataHandler;
import celestibytes.soulmagic.misc.Log;
import net.minecraft.entity.player.EntityPlayer;

public class CurseHelper {
	
	public static boolean willCurseSucceed(EntityPlayer caster, EntityPlayer target) {
		return true; // TODO: check target's NBT for curse protection
	}
	
	public static boolean hasPlayerCurseProtect(EntityPlayer player) {
		return false;
	}
	
	public static boolean hasPlayerCurse(String curseId, EntityPlayer player) {
		Iterator<ICurse> iter = DataHandler.getExtraData(player).curses.iterator();
		while(iter.hasNext()) {
			if(iter.next().getCurseId().equals(curseId)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasPlayerCurse(Class<? extends ICurse> curse, EntityPlayer player) {
		Iterator<ICurse> iter = DataHandler.getExtraData(player).curses.iterator();
		while(iter.hasNext()) {
			if(iter.next().getClass() == curse) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean addCurseToPlayer(Class<? extends ICurse> curse, EntityPlayer player) {
		PlayerExtraData data = DataHandler.getExtraData(player);
		if(data.curseSlots > data.curses.size()) {
			Iterator<ICurse> iter = data.curses.iterator();
			while(iter.hasNext()) {
				ICurse cr = iter.next();
				if(cr != null && cr.getClass() == curse) {
					Log.debug("Player already had the curse!");
					return false;
				}
			}
			try {
				ICurse curseInst = curse.newInstance();
				data.curses.add(curseInst);
				return true;
			} catch(Exception e) {
				Log.err("Couldn't add a new curse to player " + player.getDisplayName() + ", curse class: " + curse.getName());
				e.printStackTrace();
			}
		} else {
			Log.info("Not enough curse slots!");
		}
		return false;
	}
	
	public static boolean addCurseToPlayer(String curseId, EntityPlayer player) {
		Class<? extends ICurse> curse = SoulMagicAPI.getCurse(curseId);
		if(curse != null) {
			return addCurseToPlayer(curse, player);
		}
		return false;
	}
	
	public static boolean removePlayerCurse(Class<? extends ICurse> curse, EntityPlayer player) {
		PlayerExtraData data = DataHandler.getExtraData(player);
		Iterator<ICurse> iter = data.curses.iterator();
		while(iter.hasNext()) {
			ICurse cur = iter.next();
			if(cur != null && cur.getClass() == curse) {
				iter.remove();
				return true;
			}
		}
		return false;
	}
	
	public static boolean removePlayerCurse(String curseId, EntityPlayer player) {
		Class<? extends ICurse> curse = SoulMagicAPI.getCurse(curseId);
		if(curse != null) {
			return removePlayerCurse(curse, player);
		}
		return false;
	}
	
}
