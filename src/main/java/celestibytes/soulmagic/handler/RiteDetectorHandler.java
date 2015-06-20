package celestibytes.soulmagic.handler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import celestibytes.soulmagic.api.ICurse;
import celestibytes.soulmagic.api.SoulMagicAPI;
import celestibytes.soulmagic.datatypes.Tuple;
import celestibytes.soulmagic.misc.Log;
import net.minecraft.block.Block;
import net.minecraft.world.World;

public class RiteDetectorHandler {
	
	// TODO: clear these on world load
	
	private static List<RiteDetector> detectors = new LinkedList<RiteDetector>();
	
	public static void addDetector(World world, int x, int y, int z) {
		Block blc = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		Class<? extends ICurse> curseCls = SoulMagicAPI.getCurseByTrBlc(blc, meta);
		if(curseCls != null) {
			try {
				ICurse curse = curseCls.newInstance();
				RiteDetector rd = new RiteDetector(world, curse, x, y, z, new Tuple<Block, Integer>(blc, meta));
				detectors.add(rd);
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
