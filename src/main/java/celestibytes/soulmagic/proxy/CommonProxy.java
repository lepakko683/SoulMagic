package celestibytes.soulmagic.proxy;

import net.minecraftforge.common.MinecraftForge;
import celestibytes.soulmagic.handler.BlockHarvestHandler;
import celestibytes.soulmagic.handler.LivingDropHandler;
import celestibytes.soulmagic.handler.PlayerEventHandler;
import celestibytes.soulmagic.handler.RiteDetectorHandler;
import celestibytes.soulmagic.init.ModBlocks;
import celestibytes.soulmagic.init.ModCurses;
import celestibytes.soulmagic.init.ModItems;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		ModItems.init();
		//ModBlocks.init();
		ModCurses.init();
	}
	
	public void initNonClient() {
		FMLCommonHandler.instance().bus().register(new RiteDetectorHandler());
	}
	
	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new BlockHarvestHandler());
		MinecraftForge.EVENT_BUS.register(new LivingDropHandler());
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
		
		initNonClient();
	}
	
	public void postInit(FMLPostInitializationEvent e) {
		
	}
}
