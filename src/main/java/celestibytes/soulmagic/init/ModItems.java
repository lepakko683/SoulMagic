package celestibytes.soulmagic.init;

import celestibytes.soulmagic.Ref;
import celestibytes.soulmagic.items.ItemCurse;
import celestibytes.soulmagic.items.ItemRelic;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(Ref.MOD_ID)
public class ModItems {
	
	public static final ItemCurse liquidCorruptOrb = new ItemCurse();
	public static final ItemRelic relic = new ItemRelic();
	
	
	public static void init() {
		GameRegistry.registerItem(liquidCorruptOrb, "liquidCorruptOrb");
		GameRegistry.registerItem(relic, "relic");
	}
}
