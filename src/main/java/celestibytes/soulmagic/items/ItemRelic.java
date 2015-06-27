package celestibytes.soulmagic.items;

import java.util.List;

import celestibytes.soulmagic.Ref;
import celestibytes.soulmagic.handler.RiteDetectorHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemRelic extends ModItem {
	// Modes:
	// - knowledge
	// - toggle abilities
	// - start rite
	// - examine
	// - rest
	// - tool mode
	
	private static final String TRANSL_ROOT = "item." + Ref.MOD_ID + ":relic";
	private static final int NORMAL_RELIC_MODES = 5;

	private static final String[] RELIC_MODES = {
		TRANSL_ROOT + ".mode.knowledge",
		TRANSL_ROOT + ".mode.abilities",
		TRANSL_ROOT + ".mode.startRite",
		TRANSL_ROOT + ".mode.examine",
		TRANSL_ROOT + ".mode.rest",
		TRANSL_ROOT + ".mode.tool"
	};
	
	private static enum RelicType {
		RELIC_TYPE_NORMAL(0),
		RELIC_TYPE_SWORD(1),
		RELIC_TYPE_PICKAXE(2),
		RELIC_TYPE_SHOVEL(3),
		RELIC_TYPE_AXE(4);
		
		public final int typeNum;
		
		// Constructing with numbers so it's less likely to have these in the wrong order
		private RelicType(int typeNum) {
			this.typeNum = typeNum;
		}
		
	}

	public ItemRelic() {
		super("relic");
		this.setMaxStackSize(1);
		this.setMaxDamage(RelicType.values().length - 1);
		this.setHasSubtypes(true);
		this.setNoRepair();
	}
	
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer plr, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			NBTTagCompound nbt = getNBT(stack, plr);
			
			int mode = nbt.getInteger("relic_mode");
			int type = stack.getItemDamage();
			
			boolean modeSwitch = false;
			
			if(plr.isSneaking()) {
				mode++;
				modeSwitch = true;
				nbt.setInteger("relic_mode", mode);
				System.out.println("chRelicMode");
			}
			
			if(mode < 0 || mode >= NORMAL_RELIC_MODES + (type != 0 ? 1 : 0)) {
				System.out.println("reset!");
				mode = 0;
				nbt.setInteger("relic_mode", mode);
			}
			
			if(!modeSwitch) {
				switch(mode) {
				case 0:
					System.out.println("Knowledge!");
					break;
				case 1:
					System.out.println("Abilities!");
					break;
				case 2:
					RiteDetectorHandler.addDetector(world, x, y, z, plr);
					break;
				case 3:
					System.out.println("Examine!");
					break;
				case 4:
					System.out.println("Rest!");
					break;
				case 5:
					System.out.println("Tool");
					break;
				default:
					System.out.println("-unknown-");
				}
			}
		}
		return true;
	}
	
	@Override
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void addInformation(ItemStack stack, EntityPlayer plr, List info, boolean advanced) {
		super.addInformation(stack, plr, info, advanced);
		NBTTagCompound nbt = getNBT(stack, plr);
		
		int modes = NORMAL_RELIC_MODES;
		if(stack.getItemDamage() != 0) {
			modes++;
		}
		
		int mode = nbt.getInteger("relic_mode");
		
		info.add(StatCollector.translateToLocal(TRANSL_ROOT + ".owner") + ": " + EnumChatFormatting.RED + nbt.getString("relic_owner"));
		info.add("");
		
		for(int i = 0; i < modes; i++) {
			if(i == mode) {
				info.add(EnumChatFormatting.WHITE + "> " + StatCollector.translateToLocal(RELIC_MODES[i]) + " <");
			} else {
				info.add(EnumChatFormatting.DARK_GRAY + "  " + StatCollector.translateToLocal(RELIC_MODES[i]));
			}
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt != null) {
			int relicMode = nbt.getInteger("relic_mode");
			return super.getItemStackDisplayName(stack) + ": " + StatCollector.translateToLocal(RELIC_MODES[relicMode >= 0 && relicMode < RELIC_MODES.length ? relicMode : 0]);
		}
		
		return super.getItemStackDisplayName(stack);
	}
	
	private NBTTagCompound getNBT(ItemStack stack, EntityPlayer plr) {
		NBTTagCompound nbt = stack.getTagCompound();
		if(nbt == null) {
			nbt = new NBTTagCompound();
			nbt.setString("relic_owner", plr.getDisplayName());
			stack.setTagCompound(nbt);
		}
		
		return nbt;
	}
	
	// TODO: ALL THE THINGS

}
