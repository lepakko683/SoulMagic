package celestibytes.soulmagic.items;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import celestibytes.soulmagic.Ref;
import celestibytes.soulmagic.api.ICurse;
import celestibytes.soulmagic.api.SoulMagicAPI;
import celestibytes.soulmagic.datatypes.PlayerExtraData;
import celestibytes.soulmagic.handler.DataHandler;
import celestibytes.soulmagic.misc.Log;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemCurse extends ModItem {
	
	public ItemCurse() {
		super("liquidCorruptOrb");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer plr) {
		if(!world.isRemote) {
			PlayerExtraData data = DataHandler.getExtraData(plr);
			if(data != null) {
				Log.info("Curse slots: " + data.curseSlots);
			}
			
			if(stack.getTagCompound() == null) {
				stack.setTagCompound(new NBTTagCompound());
			}
			NBTTagCompound nbt = stack.getTagCompound();
			
			int curseIndex = 0;
			
			curseIndex = nbt.getInteger("curseIndex");
			
			
			
			if(!SoulMagicAPI.curseRegistry.isEmpty()) {
				Iterator<Entry<String, Class<? extends ICurse>>> iter = SoulMagicAPI.curseRegistry.entrySet().iterator();
				int i=0;
				
				if(plr.isSneaking()) {
					while(iter.hasNext()) {
						Entry<String, Class<? extends ICurse>> ent = iter.next();
						if(ent != null) {
							Log.info(ent.getKey() + " = " + ent.getValue().getName());
						}
						i++;
					}
				} else {
					while(iter.hasNext()) {
						Entry<String, Class<? extends ICurse>> ent = iter.next();
						if(ent != null && i == curseIndex) {
							Log.info("Toggle curse: " + ent.getValue().getName());
						}
						i++;
					}
				}
			}
		}
		
		return super.onItemRightClick(stack, world, plr);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer plr, List info, boolean advanced) {
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt != null) {
			info.add("Current curse: " + nbt.getInteger("curseIndex"));
		}
		super.addInformation(stack, plr, info, advanced);
	}
}
