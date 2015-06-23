package celestibytes.soulmagic.api;

import java.util.HashMap;

import celestibytes.soulmagic.misc.Log;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;

public class SoulMagicAPI {
	
	public static final HashMap<String, Class<? extends ICurse>> curseRegistry = new HashMap<String, Class<? extends ICurse>>();
	public static final HashMap<String, Class<? extends ICurse>> trBlcToCurseReg = new HashMap<String, Class<? extends ICurse>>();
	/** Only used on world-side (world.isRemote == false) */
	public static final HashMap<String, Integer> curseIdRegSI = new HashMap<String, Integer>();
	public static final HashMap<Integer, String> curseIdRegIS = new HashMap<Integer, String>();
	private static int curseNumId = 0;

	
	/** @param curseId A string id to identify the curse, should be kept as short as possible. Preferably of form "modid:curseid" e.g. "soulmagic:gltt" for Curse of Gluttony 
	 *  @param curse the curse class to register. See ICurse interface for more information.
	 *  @return the numeric id of the curse. -1 on failed registration. */
	public static int registerCurse(String curseId, Class<? extends ICurse> curse, Block trBlc, int trMeta) {
		if(!curseRegistry.containsKey(curseId)) {
			String trBlcId = GameData.getBlockRegistry().getNameForObject(trBlc);
			if(trBlcId == null) {
				Log.err("Trigger block doesn't exist in block registry");
				return -1;
			}
			trBlcId = trBlcId.concat(":" + trMeta);
			if(trBlcToCurseReg.containsKey(trBlcId)) {
				Log.err("Trigger block already used!");
				return -1;
			}
			curseRegistry.put(curseId, curse);
			trBlcToCurseReg.put(trBlcId, curse);
			curseIdRegSI.put(curseId, curseNumId);
			curseIdRegIS.put(curseNumId, curseId);
			System.out.println("Registered curse: " + curseId + " (" + curseNumId + ")");
			return curseNumId++;
		}
		
		System.out.println("Curse " + curseId + " already registered, ignoring duplicate registration.");
		return -1;	
	}
	
	public static boolean unregisterCurse(String curseId) {
		if(curseRegistry.containsKey(curseId)) {
			curseRegistry.remove(curseId);
			System.out.println("Unregistered curse: " + curseId);
			return true;
		}
		
		return false;
	}
	
	public static Class<? extends ICurse> getCurse(String curseId) {
		return curseRegistry.get(curseId);
	}
	
	public static Class<? extends ICurse> getCurseByTrBlc(String trBlcId) {
		return trBlcToCurseReg.get(trBlcId);
	}
	
	public static Class<? extends ICurse> getCurseByTrBlc(Block blc, int meta) {
		return trBlcToCurseReg.get(GameData.getBlockRegistry().getNameForObject(blc) + ":" + meta);
	}
	
	/** @return -1 if no such curse exists */
	public static int getCurseNumId(String curseId) {
		Integer ret = curseIdRegSI.get(curseId);
		
		if(ret != null) {
			return ret.intValue();
		}
		
		return -1;
	}
	
	public static String getCurseStringId(int numId) {
		String ret = curseIdRegIS.get(numId);
		
		if(ret != null) {
			return ret;
		}
		
		return null;
	}
}
