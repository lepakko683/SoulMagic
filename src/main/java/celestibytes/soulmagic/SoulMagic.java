package celestibytes.soulmagic;

import celestibytes.soulmagic.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.SidedProxy;

@Mod(modid=Ref.MOD_ID, name=Ref.MOD_NAME, version=Ref.VERSION)
public class SoulMagic {
	public static CreativeTabs creativeTab = new CreativeTabs("soulmagic:creativeTab") {
		
		@Override
		public Item getTabIconItem() {
			return Items.stick;
		}
	};
	
	@SidedProxy(clientSide = Ref.PROXY_CLIENT, serverSide = Ref.PROXY_SERVER)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
		
		proxy.initRelicGuis();
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent e) {
		proxy.onServerStarting(e);
	}
	
	@EventHandler
	public void onServerStopping(FMLServerStoppingEvent e) {
		proxy.onServerStopping(e);
	}
	
}
