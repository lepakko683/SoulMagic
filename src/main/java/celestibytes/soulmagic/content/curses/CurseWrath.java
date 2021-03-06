package celestibytes.soulmagic.content.curses;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import celestibytes.soulmagic.api.ICurse;
import celestibytes.soulmagic.datatypes.CurseRequirements;
import celestibytes.soulmagic.init.ModCurses;

public class CurseWrath implements ICurse  {
	
	public CurseWrath() {}
	
	@Override
	public int getActivationPower(EntityPlayer player) {
		return 0;
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
		return ModCurses.CURSE_WRATH;
	}

	@Override
	public CurseRequirements getCurseRequirements() {
		return null;
	}

	@Override
	public CurseRequirements getPlayerSpecificRequirements(EntityPlayer player) {
		return null;
	}

}
