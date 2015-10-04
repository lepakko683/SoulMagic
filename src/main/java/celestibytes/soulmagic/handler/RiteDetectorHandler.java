package celestibytes.soulmagic.handler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import celestibytes.soulmagic.SoulMagic;
import celestibytes.soulmagic.api.ICurse;
import celestibytes.soulmagic.api.SoulMagicAPI;
import celestibytes.soulmagic.datatypes.PlayerExtraData;
import celestibytes.soulmagic.datatypes.Tuple;
import celestibytes.soulmagic.misc.Log;
import celestibytes.soulmagic.network.PacketHandler;
import celestibytes.soulmagic.network.WorldSideHelper;
import celestibytes.soulmagic.network.message.MessageSpawnParticles;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class RiteDetectorHandler {
	
	// TODO: clear these on world load -- fixed?
	
	private static List<RiteDetector> detectors = new LinkedList<RiteDetector>();
	
	public static void clearDetectors() {
		detectors.clear();
	}
	
	public static void addDetector(World world, int x, int y, int z, EntityPlayer player) {
		PlayerExtraData data = DataHandler.getExtraData(player);
		//SoulMagic.proxy.spawnParticle("smoke", x, y, z, 0f, 1f, 0f);
		//WorldSideHelper.spawnParticle(world, "lava", x + .5f, y + 1f, z + .5f, 0f, .25f, 0f);
		if(!data.canActivateRite()) {
			WorldSideHelper.spawnParticleSpreaded(world, "largesmoke", x + .5f, y + 1f, z + .5f, 0f, .15f, 0f, .2f, .2f, 5);
			Log.info("Cannot have more than one rite at a time!");
			return;
		}
		
		Block blc = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		Class<? extends ICurse> curseCls = SoulMagicAPI.getCurseByTrBlc(blc, meta);
		if(curseCls != null) {
			try {
				ICurse curse = curseCls.newInstance();
				RiteDetector rd = new RiteDetector(world, curse, x, y, z, new Tuple<Block, Integer>(blc, meta));
				detectors.add(rd);
				data.bindRite(rd);
				Log.info("Detector added!");
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			Log.info("No curse found!");
		}
	}
	
	private int regl = 0;
	
	@SubscribeEvent
	public void tickDetectors(TickEvent.ServerTickEvent e) {
		if(!detectors.isEmpty()) {
			Iterator<RiteDetector> iter = detectors.iterator();
			while(iter.hasNext()) {
				RiteDetector rd = iter.next();
				boolean remove = rd.updateDetector(5);
				if(remove) {
					iter.remove();
					Log.info("Detector removed!");
				}
			}
		}
		
	}
}
