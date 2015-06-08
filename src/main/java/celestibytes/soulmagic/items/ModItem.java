package celestibytes.soulmagic.items;

import celestibytes.soulmagic.Ref;
import celestibytes.soulmagic.SoulMagic;
import net.minecraft.item.Item;

public class ModItem extends Item {
	
	public ModItem(String texName) {
		texName = Ref.MOD_ID + ":" + texName;
		setCreativeTab(SoulMagic.creativeTab);
		setTextureName(texName);
		setUnlocalizedName(texName);
	}
}
