package celestibytes.soulmagic.items;

import celestibytes.soulmagic.Ref;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCurse extends ModItem {
	
	public ItemCurse() {
		super("liquidCorruptOrb");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer plr) {
		if(!world.isRemote) {
			System.out.println("Hellope!");
		}
		return super.onItemRightClick(stack, world, plr);
	}
}
