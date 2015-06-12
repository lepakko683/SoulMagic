package celestibytes.soulmagic.api;


import celestibytes.soulmagic.datatypes.Tuple;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/** The constructor must be public and have no arguments */
public interface ICurse {
	
	/** The amount of power a player has to have for the curse to have an effect */
	public int getActivationPower(EntityPlayer player);
	
	public Tuple<Block, Integer> getTriggerBlock();
	
	/** The required things to cast the curse on someone, the searched area is 9x9 centered around a special block
	 *  The allowed types are:
	 *  
	 *  Integer: required power
	 *  Integer + Integer: the day time frame when the curse must be cast, set both to -1 for any time of the day [must come after required power] 
	 *  EntityLiving + Boolean: an entity required nearby, the boolean indicates whether this entity will be killed in the process
	 *  BlockBeacon + Integer: beacon with level from 0 to 4 (layers) where 0 is only the beacon block and 4 is a full beacon
	 *  BlockChest + ItemStacks + null: A chest required nearby containing the listed ItemStacks(the NBT will be ignored) with a null marking the end of the list
	 *  Block: a block required nearby*/
	public Object[] getCurseRequirements(EntityPlayer player);
	
	/**  The required things to cleanse the curse. Has the same format as getCurseRequirements() */
	public Object[] getUncurseRequirements(EntityPlayer player);
	
	/** Called when the curse process succeeds, the curse itself might still fail (e.g. if the target has curse-protection) */
	public void onCursePlayer(EntityPlayer caster, EntityPlayer target);
	
	/** Called if the curse is successful and the target was cursed. */
	public void onCursePlayerSuccess(EntityPlayer caster, EntityPlayer target);
	
	/** Called after the curse process is done, before the target is cursed the returned value determines if the target gets cursed */
	public boolean willCurseSucceed(EntityPlayer caster, EntityPlayer target);
	
	/** Load data from nbt */
	public void readFromNBT(NBTTagCompound nbt);
	
	/** Save data to nbt */
	public void writeToNBT(NBTTagCompound nbt);
	
	/** String id used to register the curse. */
	public String getCurseId();
}
