package celestibytes.soulmagic.datatypes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.google.common.io.Files;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import celestibytes.soulmagic.api.ICurse;
import celestibytes.soulmagic.api.SoulMagicAPI;
import celestibytes.soulmagic.handler.DataHandler;
import celestibytes.soulmagic.handler.RiteDetector;
import celestibytes.soulmagic.misc.Log;

public class PlayerExtraData {
	// curse data
	// soul data
	// etc..
	
	public int curseSlots = 1;
	public int soulStatus = 0; // -10 .. 10
	
	private WeakReference<RiteDetector> boundRite;
	public List<ICurse> curses;
	// list of abilities
	
	// little bit of curse caching
	private String cc_curseId = null;
	private boolean cc_retValue = false;
	private long cc_lastCheck = 0;
	
	private PlayerExtraData() {
		curses = new LinkedList<ICurse>();
	}
	
	public boolean canActivateRite() {
		return boundRite != null ? boundRite.get() == null : true;
	}
	
	public void bindRite(RiteDetector rd) {
		boundRite = new WeakReference<RiteDetector>(rd);
	}
	
	public boolean hasCurse(String curseId) {
		if(System.currentTimeMillis() - cc_lastCheck < 2000) {
			if(curseId.equals(cc_curseId)) {
				return cc_retValue;
			}
		}
		
		System.out.println("Recheck curse!");
		
		cc_retValue = false;
		Iterator<ICurse> iter = curses.iterator();
		while(iter.hasNext()) {
			if(iter.next().getCurseId().equals(curseId)) {
				cc_retValue = true;
				break;
			}
		}
		
		cc_curseId = curseId;
		cc_lastCheck = System.currentTimeMillis();
		return cc_retValue;
	}
	
	public final void writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("curseSlots", curseSlots);
		nbt.setInteger("soulStatus", soulStatus);
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
					Log.debug("Iterator doesn't support removal of elements");
				}
			}
		}
		Log.debug("Write to nbt succesful");
	}
	
	public void writeToFile(File file, File backFile) {
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		boolean fail = false;
		try {
			CompressedStreamTools.writeCompressed(nbt, new FileOutputStream(file));
			Log.debug("Write to file succesful");
		} catch (IOException e) {
			Log.err("Error while saving player extra data!");
			e.printStackTrace();
			fail = true;
		}
		
		if(!fail && !file.equals(backFile)) {
			try {
				Files.copy(file, backFile);
				Log.debug("File backup succesful");
			} catch (IOException e) {
				Log.err("Error while backing up player extra data file!");
				e.printStackTrace();
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
							Log.err("Error while loading a curse from NBT.");
							e.printStackTrace();
						}
					} else {
						Log.err("Curse doesn't have an id! This should be fixed ASAP!");
					}
				} else {
					break;
				}
				curseIndex++;
			}
		}
		return ret;
	}
	
	public static PlayerExtraData readFromFile(File data, File backFile, EntityPlayerMP player) {
		if(!isSaveValid(data) && !isSaveValid(backFile)) {
			Log.info("Save for " + player.getDisplayName() + " not found, creating new..");
			return createNew(player);
		}
		if(isSaveValid(data)) {
			try {
				NBTTagCompound nbt = CompressedStreamTools.readCompressed(new FileInputStream(data));
				if(nbt != null) {
					return readFromNBT(nbt);
				}
			} catch (IOException e) {
				Log.err("Failed to load player extra data! Attempting to load backup...");
				e.printStackTrace();
			}
		} else {
			Log.err("Invalid main save file");
		}
		
		if(isSaveValid(backFile)) {	
			try {
				NBTTagCompound nbt = CompressedStreamTools.readCompressed(new FileInputStream(backFile));
				if(nbt != null) {
					return readFromNBT(nbt);
				}
			} catch (IOException e) {
				Log.err("Failed to load player extra data backup file! Creating new data...");
				e.printStackTrace();
			}
		} else {
			Log.err("Invalid save backup file");
		}
		
		return createNew(player);
	}
	
	public static PlayerExtraData createNew(EntityPlayer player) {
		return new PlayerExtraData();
	}
	
	private static boolean isSaveValid(File save) {
		return save.exists() && save.isFile();
		
	}
	
}
