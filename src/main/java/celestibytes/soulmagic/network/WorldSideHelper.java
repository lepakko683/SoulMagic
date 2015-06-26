package celestibytes.soulmagic.network;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.NetworkRegistry;
import celestibytes.soulmagic.network.message.MessagePlaySoundFx;
import celestibytes.soulmagic.network.message.MessageSpawnParticles;

// Helper methods for integrated server / dedicated server side
public class WorldSideHelper {
	
	private static final Random rand = new Random();
	
	public static void spawnParticle(World world, String partName, float x, float y, float z, float dx, float dy, float dz) {
		PacketHandler.INSTANCE.sendToAllAround(new MessageSpawnParticles(partName, new float[]{x, y, z, dx, dy, dz}), new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 16));
	}
	
	public static void spawnParticleSpreaded(World world, String partName, float x, float y, float z, float dx, float dy, float dz, float maxSpread, float velvar, int count) {
		float[][] parts = new float[count][6];
		for(int i = 0; i < parts.length; i++) {
			parts[i][0] = x + rand.nextFloat() * maxSpread - maxSpread / 2f;
			parts[i][1] = y + rand.nextFloat() * maxSpread - maxSpread / 2f;
			parts[i][2] = z + rand.nextFloat() * maxSpread - maxSpread / 2f;
			parts[i][3] = dx + rand.nextFloat() * velvar - velvar / 2f;
			parts[i][4] = dy + rand.nextFloat() * velvar - velvar / 2f;
			parts[i][5] = dz + rand.nextFloat() * velvar - velvar / 2f;
		}
		PacketHandler.INSTANCE.sendToAllAround(new MessageSpawnParticles(partName, parts), new NetworkRegistry.TargetPoint(world.provider.dimensionId, x, y, z, 16));
	}
	
	public static void playSoundAtEntity(EntityPlayerMP plr, String soundName, Entity ent, float volume, float pitch, float radius) {
		float xd = (float) (plr.posX - ent.posX);
		float yd = (float) (plr.posY - ent.posY);
		float zd = (float) (plr.posZ - ent.posZ);
		if(radius * radius > xd * xd + yd * yd + zd * zd) {
			PacketHandler.INSTANCE.sendTo(new MessagePlaySoundFx(soundName, (float) ent.posX, (float) ent.posY, (float) ent.posZ, volume, pitch), plr);
		}
	}
	
	public static void playSound(EntityPlayerMP plr, String soundName, float x, float y, float z, float volume, float pitch, float radius) {
		float xd = (float) (plr.posX - x);
		float yd = (float) (plr.posY - y);
		float zd = (float) (plr.posZ - z);
		if(radius * radius > xd * xd + yd * yd + zd * zd) {
			PacketHandler.INSTANCE.sendTo(new MessagePlaySoundFx(soundName, x, y, z, volume, pitch), plr);
		}
	}
	
	public static void playSoundAtPlayer(EntityPlayerMP plr, String soundName, float volume, float pitch) {
		PacketHandler.INSTANCE.sendTo(new MessagePlaySoundFx(soundName, (float) plr.posX, (float) plr.posY, (float) plr.posZ, volume, pitch), plr);
	}
}
