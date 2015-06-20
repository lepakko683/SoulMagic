package celestibytes.soulmagic.api.datatypes;

import net.minecraft.entity.EntityLiving;

public interface IEntityLivingIdent {
	
	public boolean matches(EntityLiving ent);
	
	public boolean matches(IEntityLivingIdent ent);
	
}
