package celestibytes.soulmagic.api;

import java.util.HashMap;

public class SoulMagicAPI {
	
	public static HashMap<String, Class<? extends ICurse>> curseRegistry = new HashMap<String, Class<? extends ICurse>>();
	
	/** @param curseId A string id to identify the curse, should be kept as short as possible. Preferably of form "modid:curseid" e.g. "soulmagic:gltt" for Curse of Gluttony 
	 *  @param curse the curse class to register. See ICurse interface for more information. */
	public static boolean registerCurse(String curseId, Class<? extends ICurse> curse) {
		if(!curseRegistry.containsKey(curseId)) {
			curseRegistry.put(curseId, curse);
			System.out.println("Registered curse: " + curseId);
			return true;
		}
		
		System.out.println("Curse " + curseId + "already registered, ignoring duplicate registration.");
		return false;	
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
}
