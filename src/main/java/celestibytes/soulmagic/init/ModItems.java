package celestibytes.soulmagic.init;

import celestibytes.soulmagic.Ref;
import celestibytes.soulmagic.items.ItemCurse;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(Ref.MOD_ID)
public class ModItems {
	
	public static final ItemCurse liquidCorruptOrb = new ItemCurse();
	
	
	
	public static void init() {
		GameRegistry.registerItem(liquidCorruptOrb, "liquidCorruptOrb");
	}
}
