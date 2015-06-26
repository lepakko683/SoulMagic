package celestibytes.soulmagic.items;

import celestibytes.soulmagic.handler.RiteDetectorHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemRelic extends ModItem {
	// Modes:
	// - "research"
	// - toggle abilities
	// - start rite
	// - examine
	// - rest
	// - tool mode

	public ItemRelic() {
		super("relic");
		this.setMaxStackSize(1);
		this.setNoRepair();
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer plr, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			RiteDetectorHandler.addDetector(world, x, y, z, plr);
		}
		return true;
	}
	
	// TODO: ALL THE THINGS

}
