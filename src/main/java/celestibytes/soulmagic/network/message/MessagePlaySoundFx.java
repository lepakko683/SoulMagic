package celestibytes.soulmagic.network.message;

import celestibytes.soulmagic.SoulMagic;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessagePlaySoundFx implements IMessage, IMessageHandler<MessagePlaySoundFx, IMessage> {
	
	private String soundName;
	private float x, y, z;
	private float volume, pitch;
	private boolean recv_invalid = false;
	
	public MessagePlaySoundFx() {}
	
	public MessagePlaySoundFx(String soundName, float x, float y, float z, float volume, float pitch) {
		this.soundName = soundName;
		this.x = x;
		this.y = y;
		this.z = z;
		this.volume = volume;
		this.pitch = pitch;
	}

	@Override
	public IMessage onMessage(MessagePlaySoundFx msg, MessageContext ctx) {
		if(!recv_invalid) {
			SoulMagic.proxy.playSound(msg.x, msg.y, msg.z, msg.soundName, msg.volume, msg.pitch, false);
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		byte soundNameLen = buf.readByte();
		if(buf.readableBytes() < soundNameLen) {
			recv_invalid = true;
			buf.skipBytes(buf.readableBytes()); // idk if this is necessary
			return;
		}
		byte[] strBytes = new byte[soundNameLen];
		buf.readBytes(strBytes, 0, strBytes.length);
		soundName = new String(strBytes);
		x = buf.readFloat();
		y = buf.readFloat();
		z = buf.readFloat();
		volume = buf.readFloat();
		pitch = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		byte[] bytes = soundName.getBytes();
		buf.writeByte(bytes.length);
		buf.writeBytes(bytes);
		buf.writeFloat(x);
		buf.writeFloat(y);
		buf.writeFloat(z);
		buf.writeFloat(volume);
		buf.writeFloat(pitch);
	}

}
