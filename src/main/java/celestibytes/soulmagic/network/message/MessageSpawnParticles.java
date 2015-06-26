package celestibytes.soulmagic.network.message;

import celestibytes.soulmagic.SoulMagic;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageSpawnParticles implements IMessage, IMessageHandler<MessageSpawnParticles, IMessage> {
	
	private String partName;
	private float[][] partData;
	
	private boolean recv_invalid = false;
	
	public MessageSpawnParticles() {}
	
	public MessageSpawnParticles(String partName, float[]... parts) {
		this.partName = partName;
		if(parts[0].length != 6) {
			partData = new float[][] {
				{0f, 0f, 0f, 0f, 0f, 0f}
			};
		} else {
			partData = parts;
		}
		
	}

	@Override
	public IMessage onMessage(MessageSpawnParticles msg, MessageContext ctx) {
		if(!msg.recv_invalid) {
			for(float[] part : msg.partData) {
				SoulMagic.proxy.spawnParticle(msg.partName, part[0], part[1], part[2], part[3], part[4], part[5]);
			}
			System.out.println("Spawn!");
		} else {
			System.out.println("invalid!");
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		byte partNameLen = buf.readByte();
		if(buf.readableBytes() < partNameLen) {
			recv_invalid = true;
			buf.skipBytes(buf.readableBytes()); // idk if this is necessary
			return;
		}
		byte[] strBytes = new byte[partNameLen];
		buf.readBytes(strBytes, 0, strBytes.length);
		partName = new String(strBytes);
		
		int parts = buf.readableBytes() / (6 * 4);
		partData = new float[parts][6];
		for(int i = 0; i < parts; i++) {
			partData[i][0] = buf.readFloat();
			partData[i][1] = buf.readFloat();
			partData[i][2] = buf.readFloat();
			partData[i][3] = buf.readFloat();
			partData[i][4] = buf.readFloat();
			partData[i][5] = buf.readFloat();
		}
		
		buf.skipBytes(buf.readableBytes()); // idk if this is necessary
	}

	@Override
	public void toBytes(ByteBuf buf) {
		byte[] bytes = partName.getBytes();
		buf.writeByte(bytes.length & 127);
		buf.writeBytes(bytes);
		for(float[] part : partData) {
			buf.writeFloat(part[0]);
			buf.writeFloat(part[1]);
			buf.writeFloat(part[2]);
			buf.writeFloat(part[3]);
			buf.writeFloat(part[4]);
			buf.writeFloat(part[5]);
		}
		
	}

}
