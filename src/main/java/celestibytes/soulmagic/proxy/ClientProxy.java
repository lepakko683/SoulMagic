package celestibytes.soulmagic.proxy;

import cpw.mods.fml.client.FMLClientHandler;

public class ClientProxy extends CommonProxy {
	
	public void spawnParticle(String partName, float x, float y, float z, float dx, float dy, float dz) {
		FMLClientHandler.instance().getWorldClient().spawnParticle(partName, x, y, z, dx, dy, dz);
	}
	
	public void playSound(float x, float y, float z, String soundName, float volume, float pitch, boolean b) {
		FMLClientHandler.instance().getClient().theWorld.playSound(x, y, z, soundName, volume, pitch, b);
	}
}
