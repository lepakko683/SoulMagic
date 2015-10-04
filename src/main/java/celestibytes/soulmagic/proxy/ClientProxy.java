package celestibytes.soulmagic.proxy;

import celestibytes.soulmagic.api.SoulMagicAPI;
import celestibytes.soulmagic.api.datatypes.PageGuiInfo;
import cpw.mods.fml.client.FMLClientHandler;

public class ClientProxy extends CommonProxy {
	
	public void initRelicGuis() {
		System.out.println("register: " + SoulMagicAPI.registerKnowledgePage(new PageGuiInfo("knowledge.examine.examining", "examining.title.key", "examining.desc.key", "examining.doc.key")));
		System.out.println("register: " + SoulMagicAPI.registerKnowledgePage(new PageGuiInfo("knowledge.examine.monsters.zombie", "zombie.title.key", "zombie.desc.key", "zombie.doc.key")));
		System.out.println("register: " + SoulMagicAPI.registerKnowledgePage(new PageGuiInfo("knowledge.examine.monsters.creeper", "creeper.title.key", "creeper.desc.key", "creeper.doc.key")));
		
		System.out.println("get: " + SoulMagicAPI.getKnowledgePage("knowledge.examine.monsters.zombie"));
		System.out.println("get: " + SoulMagicAPI.getKnowledgePage("knowledge.examine.monsters.creeper"));
		System.out.println("get: " + SoulMagicAPI.getKnowledgePage("knowledge.examine.examining"));
	}
	
	public void spawnParticle(String partName, float x, float y, float z, float dx, float dy, float dz) {
		FMLClientHandler.instance().getWorldClient().spawnParticle(partName, x, y, z, dx, dy, dz);
	}
	
	public void playSound(float x, float y, float z, String soundName, float volume, float pitch, boolean b) {
		FMLClientHandler.instance().getClient().theWorld.playSound(x, y, z, soundName, volume, pitch, b);
	}
}
