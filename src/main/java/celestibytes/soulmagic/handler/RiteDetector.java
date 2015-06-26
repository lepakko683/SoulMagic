package celestibytes.soulmagic.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import celestibytes.soulmagic.api.CurseHelper;
import celestibytes.soulmagic.api.ICurse;
import celestibytes.soulmagic.api.datatypes.BlockPos;
import celestibytes.soulmagic.api.datatypes.IEntityLivingIdent;
import celestibytes.soulmagic.datatypes.CurseRequirements;
import celestibytes.soulmagic.datatypes.Triple;
import celestibytes.soulmagic.datatypes.Tuple;
import celestibytes.soulmagic.misc.Log;
import celestibytes.soulmagic.network.WorldSideHelper;

// will be removed if the trigger block is removed

public class RiteDetector {
	
	// Coordinates are inclusive
	private static final int DEFAULT_TICK_LIMIT = 10;
	private static final int MIN_H = -4, MAX_H = 4, MIN_V = -2, MAX_V = 5;
	
	private World world;
	private ICurse curse;
	private Tuple<Block, Integer> trigger;
	
	// Center coordinates
	private final int cx, cy, cz;
	private AxisAlignedBB checkArea;
	
	// current check position
	private int x, y, z, status;
	
	private List<Tuple<EntityPlayer, CurseRequirements>> targets;
//	private Iterator<Tuple<EntityPlayer, CurseRequirements>> targetIter;
//	private EntityPlayer currTarget;
//	private CurseRequirements currReq;
	
	private CurseRequirements requirements;
	private CurseRequirements.Done reqDone;
	
	private boolean checkBlocks, checkEntities;
	
	// whether this detector is waiting for a player to pop by
	private boolean sleep;
	
	private int regulator;
	
	private boolean addInitialTargets = true;
	
	public RiteDetector(World world, ICurse curse, int cx, int cy, int cz, Tuple<Block, Integer> trigger) {
		this.world = world;
		this.curse = curse;
		this.trigger = trigger;
		this.requirements = curse.getCurseRequirements();
		this.reqDone = new CurseRequirements.Done();
		this.targets = new LinkedList<Tuple<EntityPlayer, CurseRequirements>>();
		this.cx = cx;
		this.cy = cy;
		this.cz = cz;
		this.x = MIN_H;
		this.y = MIN_V;
		this.z = MIN_H;
		this.checkArea = AxisAlignedBB.getBoundingBox(cx + MIN_H, cy + MIN_V, cz + MIN_H, cx + MAX_H, cy + MAX_V, cz + MAX_H); // TODO: testing
		this.checkBlocks = true;
		this.checkEntities = true;
		this.sleep = false;
		this.regulator = 0;
	}
	
	/**	called every tick, lowest maxTicks can be is 1, -1 is unlimited but this method is still expected to return relatively fast.
	 *  @return true if this detector should be removed (i.e. the curse is done or the trigger block was removed) */
	public boolean updateDetector(int maxTicks) {
		if(maxTicks == -1) {
			maxTicks = DEFAULT_TICK_LIMIT;
		}
		regulator++;
		if(regulator > 600) {
			regulator = 0;
		}
		
		if(sleep) {
			if(regulator == 0) {
				sleep = !detectPlayer();
			} else {
				return false;
			}
			
			if(sleep) {
				return false;
			}
		}
		
		if(regulator % 20 == 0) {
			if(world.getBlock(cx, cy, cz) != trigger.a || world.getBlockMetadata(cx, cy, cz) != trigger.b.intValue()) {
				return true;
			}
			
		}
		
		if(regulator % 200 == 0 || addInitialTargets) { // check targeted players every 10 seconds
			addInitialTargets = false;
			@SuppressWarnings("unchecked")
			List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, checkArea);
			if(players == null) {
				sleep = true;
			} else if(players.isEmpty()) {
				sleep = true;
			} else {
				if(targets.isEmpty()) {
					Iterator<EntityPlayer> iter2 = players.iterator();
					
					while(iter2.hasNext()) {
						EntityPlayer plr = iter2.next();
						CurseRequirements plrsReq = null; //curse.getPlayerSpecificRequirements(plr);
						targets.add(new Tuple<EntityPlayer, CurseRequirements>(plr, plrsReq));
					}
				} else {
					Iterator<Tuple<EntityPlayer, CurseRequirements>> iter1 = targets.iterator();
					
					while(iter1.hasNext()) {
						EntityPlayer tgt = iter1.next().a;
						Iterator<EntityPlayer> iter2 = players.iterator();
	
						boolean targetAround = false;
						while(iter2.hasNext()) {
							EntityPlayer plr = iter2.next();
							if(tgt == plr) {
								targetAround = true;
							} else {
								CurseRequirements plrsReq = null; //curse.getPlayerSpecificRequirements(plr);
								targets.add(new Tuple<EntityPlayer, CurseRequirements>(plr, plrsReq));
							}
						}
						
						if(!targetAround) {
							iter1.remove();
							continue;
						}
					}
				}
			}
			
			return false;
		}
		
		/* fix this code for player-specific curse requirements
		if(currTarget == null) {
			if(targets != null && !targets.isEmpty()) {
				if(targetIter == null) {
					targetIter = targets.iterator();
				}
				
				if(targetIter.hasNext()) {
					Tuple<EntityPlayer, CurseRequirements> ret = targetIter.next();
					currTarget = ret.a;
					currReq = ret.b;
				}
			}
		}
		
		if(currTarget != null) {
			int i = 0;
			for(int yy = cy + y; yy <= cy + MAX_V; yy++) {
				for(int zz = cz + z; zz <= cz + MAX_H; zz++) {
					for(int xx = cx + x; xx <= cx + MAX_H; xx++) {
						
						
						i++;
						if(i >= maxTicks) {
							
						}
					}
					x = MIN_H;
				}
				z = MIN_H;
			}
			y = MIN_H;
		}
		*/
		
		if(checkBlocks) {
			int i = 0;
			for(; y <= MAX_V; y++) {
				int yy = cy + y;
				for(; z <= MAX_H; z++) {
					int zz = cz + z;
					for(; x <= MAX_H; x++) {
						int xx = cx + x;
						if(i >= maxTicks) {
							return false;
						}
						Block cBlc = world.getBlock(xx, yy, zz);
						int cMeta = world.getBlockMetadata(xx, yy, zz);
						
						if(requirements.isRequired(cBlc, cMeta)) {
							reqDone.normalBlocks.add(new Tuple<Tuple<Block, Integer>, BlockPos>(new Tuple<Block, Integer>(cBlc, cMeta), new BlockPos(xx, yy, zz)));
							Log.info("found required block: " + cBlc.getUnlocalizedName());
						}
						
						i++;
					}
					x = MIN_H;
				}
				z = MIN_H;
			}
			y = MIN_H;
			checkBlocks = false;
			return false;
		}
		
		if(checkEntities) {
			@SuppressWarnings("rawtypes")
			List cEnts = world.getEntitiesWithinAABB(EntityLiving.class, checkArea);
			if(cEnts != null && !cEnts.isEmpty()) {
				List<Tuple<IEntityLivingIdent, Integer>> reqEnts = requirements.getRequiredEntities();
				@SuppressWarnings("rawtypes")
				Iterator iter = cEnts.iterator();
				while(iter.hasNext()) {
					EntityLiving cEnt = (EntityLiving) iter.next();
					Iterator<Tuple<IEntityLivingIdent, Integer>> iter2 = reqEnts.iterator();
					while(iter2.hasNext()) {
						Tuple<IEntityLivingIdent, Integer> ent2 = iter2.next();
						if(ent2.a.matches(cEnt)) {
							reqDone.entities.add(new Tuple<IEntityLivingIdent, EntityLiving>(ent2.a, cEnt));
						}
					}
				}
			}
			checkEntities = false;
			return false;
		}
		
		if(targets != null && !targets.isEmpty() && reqDone.validate(requirements, world)) {
			doCurse();
		} else {
			WorldSideHelper.spawnParticleSpreaded(world, "largesmoke", cx + .5f, cy + 1f, cz + .5f, 0f, .15f, 0f, .2f, .2f, 5);
			Log.info("Curse invalid! targets==null: " + (targets == null) + " targets.isEmpty: " + (targets == null ? "null" : targets.isEmpty()));
		}
		
		
		return true;
	}
	
	private void doCurse() {
		Iterator<Tuple<EntityPlayer, CurseRequirements>> iter = targets.iterator();
		while(iter.hasNext()) {
			Tuple<EntityPlayer, CurseRequirements> plr = iter.next();
			boolean boo = CurseHelper.addCurseToPlayer(curse.getClass(), plr.a);
			//Log.info("Add curse to: " + plr.a.getDisplayName() + ": " + boo);
//			world.playSoundAtEntity(plr.a, "mob.enderdragon.growl", 1f, .5f);
			if(plr.a instanceof EntityPlayerMP) {
				WorldSideHelper.playSoundAtPlayer((EntityPlayerMP) plr.a, "mob.enderdragon.growl", 1f, .5f);
			}
			
			if(boo) {
				WorldSideHelper.spawnParticleSpreaded(world, "flame", (float) plr.a.posX, (float) plr.a.posY + plr.a.eyeHeight, (float) plr.a.posZ, 0f, 0f, 0f, 1f, .25f, 10);
				Log.info(plr.a.getDisplayName() + " has been cursed with: " + curse.getCurseId());
			}
		}
	}
	
	private boolean detectPlayer() {
		return world.getClosestPlayer(cx, cy, cz, 9) != null;
	}
	
}
