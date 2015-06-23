package celestibytes.soulmagic.content.curses;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import celestibytes.soulmagic.api.ICurse;
import celestibytes.soulmagic.api.datatypes.IEntityLivingIdent;
import celestibytes.soulmagic.datatypes.CurseRequirements;
import celestibytes.soulmagic.datatypes.Tuple;
import celestibytes.soulmagic.init.ModCurses;

public class CurseGluttony implements ICurse {
	
	public static final Tuple<Block, Integer> trigger = new Tuple<Block, Integer>(Blocks.gold_block, 0);
	
	private static final IEntityLivingIdent pigIdent = new IEntityLivingIdent() {
		
		@Override
		public boolean matches(IEntityLivingIdent ent) {
			return ent == this;
		}
		
		@Override
		public boolean matches(EntityLiving ent) {
			return ent.getClass() == EntityPig.class;
		}
	};
	
	private static CurseRequirements requirements = new CurseRequirements(0).setTimeFrame(0, 6000);
	
	static {
		List<Tuple<Block,Integer>> blocks = new LinkedList<Tuple<Block,Integer>>();
		blocks.add(new Tuple<Block, Integer>(Blocks.lapis_block, 0));
		blocks.add(new Tuple<Block, Integer>(Blocks.lapis_block, 0));
		blocks.add(new Tuple<Block, Integer>(Blocks.cake, 0));
		blocks.add(new Tuple<Block, Integer>(Blocks.cake, 0));
		blocks.add(new Tuple<Block, Integer>(Blocks.cake, 0));
		blocks.add(new Tuple<Block, Integer>(Blocks.cake, 0));
		blocks.add(new Tuple<Block, Integer>(Blocks.cake, 0));
		List<IEntityLivingIdent> ents = new LinkedList<IEntityLivingIdent>();
		ents.add(pigIdent);
		requirements.setRequiredBlocks(blocks);
		requirements.setRequiredEntities(ents);
	}
	
	public CurseGluttony() {}

	@Override
	public int getActivationPower(EntityPlayer player) {
		return 0;
	}

	@Override
	public CurseRequirements getCurseRequirements() {
		return requirements;
	}
	
	public CurseRequirements getPlayerSpecificRequirements(EntityPlayer player) {
		return null;
	}

	@Override
	public void onCursePlayer(EntityPlayer caster, EntityPlayer target) {
		System.out.println("Attempting curse...");
	}

	@Override
	public void onCursePlayerSuccess(EntityPlayer caster, EntityPlayer target) {
		System.out.println("Player successfully cursed!");
	}

	@Override
	public boolean willCurseSucceed(EntityPlayer caster, EntityPlayer target) {
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("id", getCurseId()); // important!
	}

	@Override
	public String getCurseId() {
		return ModCurses.CURSE_GLUTTONY;
	}

}
