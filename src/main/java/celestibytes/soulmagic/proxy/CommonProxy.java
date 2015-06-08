package celestibytes.soulmagic.proxy;

import net.minecraftforge.common.MinecraftForge;
import celestibytes.soulmagic.handler.BlockHarvestHandler;
import celestibytes.soulmagic.handler.LivingDropHandler;
import celestibytes.soulmagic.init.ModBlocks;
import celestibytes.soulmagic.init.ModItems;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent e) {
		ModItems.init();
		//ModBlocks.init();
	}
	
	public void init(FMLInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new BlockHarvestHandler());
		MinecraftForge.EVENT_BUS.register(new LivingDropHandler());
	}
	
	public void postInit(FMLPostInitializationEvent e) {
		
	}
}
