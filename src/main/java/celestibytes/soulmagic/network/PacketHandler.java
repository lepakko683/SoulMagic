package celestibytes.soulmagic.network;

import celestibytes.soulmagic.Ref;
import celestibytes.soulmagic.network.message.MessagePlaySoundFx;
import celestibytes.soulmagic.network.message.MessageSpawnParticles;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Ref.MOD_ID.toLowerCase());
	
	public static void init() {
		INSTANCE.registerMessage(MessageSpawnParticles.class, MessageSpawnParticles.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(MessagePlaySoundFx.class, MessagePlaySoundFx.class, 1, Side.CLIENT);
	}
}
