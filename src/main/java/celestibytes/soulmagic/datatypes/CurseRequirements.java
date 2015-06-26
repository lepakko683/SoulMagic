package celestibytes.soulmagic.datatypes;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;
import celestibytes.soulmagic.api.datatypes.BlockPos;
import celestibytes.soulmagic.api.datatypes.IEntityLivingIdent;
import celestibytes.soulmagic.misc.Log;

public class CurseRequirements {
	public static class Done {
		public List<Tuple<Tuple<Block, Integer>, BlockPos>> normalBlocks;
		
		public List<Tuple<IEntityLivingIdent, EntityLiving>> entities;
		
		public Done() {
			normalBlocks = new LinkedList<Tuple<Tuple<Block, Integer>, BlockPos>>();
			entities = new LinkedList<Tuple<IEntityLivingIdent, EntityLiving>>();
		}
		
		public boolean validate(CurseRequirements req, World world) {
			if(!req.isTimeCorrect(world.getWorldTime(), !world.provider.isSurfaceWorld())) {
				Log.info("incorrect time for curse!");
				return false;
			}
			
			if(req.normalBlocks != null && !req.normalBlocks.isEmpty()) {
				Iterator<Triple<Block, Integer, Integer>> iter1 = req.getRequiredBlocks().iterator();
				while(iter1.hasNext()) {
					Triple<Block, Integer, Integer> blc1 = iter1.next();
					Iterator<Tuple<Tuple<Block, Integer>, BlockPos>> iter2 = normalBlocks.iterator();
					int found = 0;
					while(iter2.hasNext()) {
						Tuple<Tuple<Block, Integer>, BlockPos> blc2 = iter2.next();
						if(blc1.a == blc2.a.a && blc1.b.intValue() == blc2.a.b.intValue()) {
							if(world.getBlock(blc2.b.x, blc2.b.y, blc2.b.z) == blc1.a && world.getBlockMetadata(blc2.b.x, blc2.b.y, blc2.b.z) == blc1.b.intValue()) {
								found++;
							}
						}
					}
					Log.info("[blocks] Found: " + found + "x " + blc1.a.getUnlocalizedName());
					if(found < blc1.c.intValue()) {
						return false;
					}
				}
			}
			
			if(!req.entities.isEmpty()) {
				Iterator<Tuple<IEntityLivingIdent, Integer>> iter1 = req.getRequiredEntities().iterator();
				while(iter1.hasNext()) {
					Tuple<IEntityLivingIdent, Integer> ent1 = iter1.next();
					Iterator<Tuple<IEntityLivingIdent, EntityLiving>> iter2 = entities.iterator();
					int found = 0;
					while(iter2.hasNext()) {
						Tuple<IEntityLivingIdent, EntityLiving> ent2 = iter2.next();
						if(ent1.a.matches(ent2.b)) {
							found++;
						}
					}
					Log.info("[entities] Found: " + found);
					if(found < ent1.b.intValue()) {
						return false;
					}
				}
			}
			
			return true;
		}
	}
	
	private int requiredEnergy;
	private long startTime, endTime;
	private boolean allowTimeless;
	
	private List<Triple<Block, Integer, Integer>> normalBlocks;
	
	private List<Tuple<IEntityLivingIdent, Integer>> entities;
	
	public CurseRequirements(int requiredEnergy) {
		this.requiredEnergy = requiredEnergy;
        startTime = -1;
        endTime = -1;
        allowTimeless = false;
        normalBlocks = new LinkedList<Triple<Block, Integer, Integer>>();
        entities = new LinkedList<Tuple<IEntityLivingIdent, Integer>>();
	}
	
	public CurseRequirements setAllowTimeless(boolean value) {
		this.allowTimeless = value;
		return this;
	}
	
	public CurseRequirements setTimeFrame(long start, long end) {
		this.startTime = start;
		this.endTime = end;
		return this;
	}
	
	public CurseRequirements setRequiredBlocks(List<Tuple<Block, Integer>> blocks) {
		if(blocks != null) {
			normalBlocks.clear();
			Iterator<Tuple<Block, Integer>> iter = blocks.iterator();
			while(iter.hasNext()) {
				Tuple<Block, Integer> blc = iter.next();
				int count = 0;
				
				if(normalBlocks.isEmpty()) {
					Iterator<Tuple<Block, Integer>> iter2 = blocks.iterator();
					while(iter2.hasNext()) {
						Tuple<Block, Integer> blc2 = iter2.next();
						if(blc.a == blc2.a && blc.b.intValue() == blc2.b.intValue()) {
							count++;
						}
					}
					
					if(count > 0) {
						normalBlocks.add(new Triple<Block, Integer, Integer>(blc.a, blc.b, count));
						Log.info("added " + count + "x " + blc.a.getUnlocalizedName());
					}
				} else {
					Iterator<Triple<Block, Integer, Integer>> iter2 = normalBlocks.iterator();
					boolean exists = false;
					while(iter2.hasNext()) {
						Triple<Block, Integer, Integer> blc2 = iter2.next();
						if(blc.a == blc2.a && blc.b.intValue() == blc2.b.intValue()) {
							exists = true;
							break;
						}
					}
					
					if(!exists) {
						Iterator<Tuple<Block, Integer>> iter3 = blocks.iterator();
						while(iter3.hasNext()) {
							Tuple<Block, Integer> blc3 = iter3.next();
							if(blc.a == blc3.a && blc.b.intValue() == blc3.b.intValue()) {
								count++;
							}
						}
						
						if(count > 0) {
							normalBlocks.add(new Triple<Block, Integer, Integer>(blc.a, blc.b, count));
							Log.info("added " + count + "x " + blc.a.getUnlocalizedName());
						}
					}
				}
			}
		}
		
		return this;
	}
	
	public CurseRequirements setRequiredEntities(List<IEntityLivingIdent> entities) {
		if(entities != null) {
			this.entities.clear();
			Iterator<IEntityLivingIdent> iter = entities.iterator();
			while(iter.hasNext()) {
				IEntityLivingIdent ent1 = iter.next();
				int count = 0;
				
				if(this.entities.isEmpty()) {
					Iterator<IEntityLivingIdent> iter2 = entities.iterator();
					while(iter2.hasNext()) {
						IEntityLivingIdent ent2 = iter2.next();
						if(ent1.matches(ent2)) {
							count++;
						}
					}
					
					if(count > 0) {
						this.entities.add(new Tuple<IEntityLivingIdent, Integer>(ent1, count));
					}
				} else {
					Iterator<Tuple<IEntityLivingIdent, Integer>> iter2 = this.entities.iterator();
					boolean exists = false;
					while(iter2.hasNext()) {
						Tuple<IEntityLivingIdent, Integer> ent2 = iter2.next();
						if(ent1.matches(ent2.a)) {
							exists = true;
							break;
						}
					}
					
					if(!exists) {
						Iterator<IEntityLivingIdent> iter3 = entities.iterator();
						while(iter3.hasNext()) {
							IEntityLivingIdent ent3 = iter3.next();
							if(ent1.matches(ent3)) {
								count++;
							}
						}
						
						if(count > 0) {
							this.entities.add(new Tuple<IEntityLivingIdent, Integer>(ent1, count));
						}
					}
				}
			}
		}
		
		return this;
	}
	
	public boolean isRequired(Block blc, int meta) {
		Iterator<Triple<Block, Integer, Integer>> iter = normalBlocks.iterator();
		while(iter.hasNext()) {
			Triple<Block, Integer, Integer> blc2 = iter.next();
			if(blc == blc2.a && meta == blc2.b.intValue()) {
				return true;
			}
		}
		
		return false;
	}
	
	public int getRequiredEnergy() {
		return requiredEnergy;
	}
	
	public List<Triple<Block, Integer, Integer>> getRequiredBlocks() {
		return normalBlocks.isEmpty() ? null : normalBlocks;
	}
	
	public List<Tuple<IEntityLivingIdent, Integer>> getRequiredEntities() {
		return entities.isEmpty() ? null : entities;
	}
	
	public boolean isTimeCorrect(long worldTime, boolean timeless) {
		if(startTime == -1 || endTime == -1) {
			return true;
		}
		
		if(timeless) {
			return allowTimeless;
		}
		
		return worldTime >= startTime && worldTime < endTime;
	}
}
