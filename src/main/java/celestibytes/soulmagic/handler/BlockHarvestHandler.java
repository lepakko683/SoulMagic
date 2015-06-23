package celestibytes.soulmagic.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import celestibytes.soulmagic.api.CurseHelper;
import celestibytes.soulmagic.content.curses.CurseGluttony;
import celestibytes.soulmagic.datatypes.Tuple;
import celestibytes.soulmagic.init.ModCurses;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent;

public class BlockHarvestHandler {
	
	public interface SpecialDropBehav {
		/** @return any extra ItemStacks dropped. For extra of normal drops, set the stacksize of the passed ItemStack.
		 *  @param first true if this is the first call to the function per for single break action */
		public ItemStack[] getExtraDrops(ItemStack drop, int i, int count);
	}
	
	private static final HashMap<Block, Tuple<Integer, Object>> extraDrops = new HashMap<Block, Tuple<Integer, Object>>();
	
	static {
		extraDrops.put(Blocks.diamond_ore, new Tuple<Integer, Object>(0, new Tuple<ItemStack, Float>(new ItemStack(Items.diamond), 1.5f)));
		extraDrops.put(Blocks.emerald_ore, new Tuple<Integer, Object>(0, new Tuple<ItemStack, Float>(new ItemStack(Items.emerald), 1.5f)));
		extraDrops.put(Blocks.coal_ore, new Tuple<Integer, Object>(0, new Tuple<ItemStack, Float>(new ItemStack(Items.coal), 2.5f)));
		extraDrops.put(Blocks.redstone_ore, new Tuple<Integer, Object>(-1, new Tuple<ItemStack, Float>(new ItemStack(Items.redstone), 2.5f)));
		extraDrops.put(Blocks.lapis_ore, new Tuple<Integer, Object>(0, new Tuple<ItemStack, Float>(new ItemStack(Items.dye, 1, 4), 5.0f)));
		extraDrops.put(Blocks.quartz_ore, new Tuple<Integer, Object>(0, new Tuple<ItemStack, Float>(new ItemStack(Items.quartz), 2.0f)));
		
//		extraDrops.put(Blocks.leaves, new Tuple<Integer, Object>(-1, new Tuple<ItemStack, Float>(new ItemStack(Item.getItemFromBlock(Blocks.leaves)), 2.25f)));
		
		extraDrops.put(Blocks.melon_block, new Tuple<Integer, Object>(0, new SpecialDropBehav() {
			@Override
			public ItemStack[] getExtraDrops(ItemStack drop, int i, int count) {
				if(i == 0) {
					drop.stackSize = 9 - count + 1;
				}
				
				return null;
			}
		}));
		extraDrops.put(Blocks.glowstone, new Tuple<Integer, Object>(0, new SpecialDropBehav() {
			@Override
			public ItemStack[] getExtraDrops(ItemStack drop, int i, int count) {
				if(i == 0) {
					drop.stackSize = 4 - count + 1;
				}
				
				return null;
			}
		}));
	}
	
	private static Random rand = new Random();
	
	@SubscribeEvent
	public void onEvent(BlockEvent.HarvestDropsEvent e) {
		if(e.harvester != null && !e.isSilkTouching && !e.world.isRemote) {
			if(CurseHelper.hasPlayerCurse(CurseGluttony.class, e.harvester)) {
				// TODO: check if player has curse of gluttony
				Tuple<Integer, Object> drp = extraDrops.get(e.block);
				if(drp != null) {
					if(drp.a.intValue() == -1 || drp.a.intValue() == e.blockMetadata) {
						if(drp.b instanceof Tuple) {
							Tuple<?,?> drop = (Tuple<?,?>) drp.b;
							if(drop.a instanceof ItemStack && drop.b instanceof Float) {
								float multip = (drop == null ? -1f : ((Float)drop.b).floatValue()  * (rand.nextFloat() + 0.25f));
								ItemStack addDrop = ((ItemStack)drop.a).copy();
								addDrop.stackSize = (int) Math.floor(((float)addDrop.stackSize) * multip);
								e.drops.add(addDrop);
							}
						} else if(drp.b instanceof SpecialDropBehav) {
							SpecialDropBehav drop = (SpecialDropBehav) drp.b;
							Iterator<ItemStack> iter = e.drops.iterator();
							LinkedList<ItemStack> extExtDrops = new LinkedList<ItemStack>();
							int i=0;
							while(iter.hasNext()) {
								ItemStack is = iter.next();
								ItemStack[] ext = drop.getExtraDrops(is, i, e.drops.size());
								if(ext != null) {
									for(ItemStack ist : ext) {
										if(ist != null) {
											extExtDrops.add(ist);
										}
									}
								}
								i++;
							}
							
							e.drops.addAll(extExtDrops);
						}
					}
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	private boolean itemsEqual(ItemStack a, ItemStack b) {
		if(a == null || b == null) {
			return false;
		}
		
		return a.getItem() == b.getItem() && a.getItemDamage() == b.getItemDamage();
	}
}
