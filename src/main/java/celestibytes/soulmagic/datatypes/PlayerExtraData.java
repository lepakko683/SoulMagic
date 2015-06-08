package celestibytes.soulmagic.datatypes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import celestibytes.soulmagic.api.ICurse;
import celestibytes.soulmagic.api.SoulMagicAPI;

public class PlayerExtraData {
	// curse data
	// soul data
	// etc..
	
	public List<ICurse> curses;
	// list of abilities
	
	public int curseSlots;
	public int soulStatus; // -10 .. 10
	
	private PlayerExtraData() {
		curses = new LinkedList<ICurse>();
	}
	
	public final void writeToNBT(NBTTagCompound nbt) {
		Iterator<ICurse> iter = curses.iterator();
		int curseIndex = 0;
		while(iter.hasNext()) {
			ICurse curse = iter.next();
			if(curse != null) {
				NBTTagCompound curseNbt = new NBTTagCompound();
				curse.writeToNBT(curseNbt);
				nbt.setTag("curse_" + curseIndex, curseNbt);
			} else {
				try {
					iter.remove();
				} catch(UnsupportedOperationException e) {
					// no need to spam the log
				}
			}
		}
		
	}
	
	public static PlayerExtraData readFromNBT(NBTTagCompound nbt) {
		PlayerExtraData ret = new PlayerExtraData();
		if(!nbt.hasNoTags()) {
			int curseIndex = 0;
			while(true) {
				if(nbt.hasKey("curse_" + curseIndex)) {
					NBTTagCompound curseNbt = nbt.getCompoundTag("curse_" + curseIndex);
					String curseId = curseNbt.getString("id");
					if(!curseId.isEmpty()) {
						Class<? extends ICurse> curseType = SoulMagicAPI.getCurse(curseId);
						try {
							ICurse curse = curseType.newInstance();
							curse.readFromNBT(curseNbt);
							ret.curses.add(curse);
						} catch(Exception e) {
							System.out.println("Error while loading a curse from NBT.");
							e.printStackTrace();
						}
					} else {
						System.out.println("Curse doesn't have an id! This should be fixed ASAP!");
					}
				} else {
					break;
				}
				curseIndex++;
			}
		}
		return ret;
	}
	
}
