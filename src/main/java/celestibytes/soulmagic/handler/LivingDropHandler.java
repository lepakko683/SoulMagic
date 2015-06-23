package celestibytes.soulmagic.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import celestibytes.soulmagic.api.CurseHelper;
import celestibytes.soulmagic.content.curses.CurseGluttony;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

public class LivingDropHandler {
	
	public interface DropHandler {
		public void addDrops(EntityLivingBase killed, EntityPlayer player, ArrayList<EntityItem> drops);
	}
	
	private static final HashMap<Class<? extends EntityLivingBase>, DropHandler> dropHandlers = new HashMap<Class<? extends EntityLivingBase>, DropHandler>();
	
	static {
		dropHandlers.put(EntitySkeleton.class, new DropHandler() {
			@Override
			public void addDrops(EntityLivingBase killed, EntityPlayer player, ArrayList<EntityItem> drops) {
				EntitySkeleton skel = (EntitySkeleton) killed;
				Random rand = killed.getRNG();
				if(skel.getSkeletonType() == 1 && rand.nextInt(10) == 0) {
					dropItem(drops, killed, new ItemStack(Items.skull, 1, 1));
				}
				
				float chance = (6f * rand.nextFloat());
				int count = rand.nextInt((int) (chance < 1f ? 1f : chance));
				if(count > 0) {
					dropItem(drops, killed, new ItemStack(Items.bone, count));
				}
				
				chance = (5f * rand.nextFloat());
				count = rand.nextInt((int) (chance < 1f ? 1f : chance));
				if(count > 0) {
					dropItem(drops, killed, new ItemStack(Items.arrow, count));
				}
			}
		});
	}
	
	@SubscribeEvent
	public void onEvent(LivingDropsEvent e) {
		if(!e.entityLiving.worldObj.isRemote && e.source instanceof EntityDamageSource) {
			if(((EntityDamageSource) e.source).getEntity() instanceof EntityPlayer) {
				EntityPlayer plr = (EntityPlayer) ((EntityDamageSource)e.source).getEntity();
				if(CurseHelper.hasPlayerCurse(CurseGluttony.class, plr)) {
					DropHandler dh = dropHandlers.get(e.entityLiving.getClass());
					
					if(dh != null) {
						dh.addDrops(e.entityLiving, plr, e.drops);
					}
				}
			}
		}
	}
	
	private static void dropItem(ArrayList<EntityItem> drops, EntityLivingBase entity, ItemStack item) {
		EntityItem entityitem = new EntityItem(entity.worldObj, entity.posX, entity.posY, entity.posZ, item);
		entityitem.delayBeforeCanPickup = 10;
		drops.add(entityitem);
	}
}
