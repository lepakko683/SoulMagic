package celestibytes.soulmagic.network.message;

import celestibytes.soulmagic.Ref;
import celestibytes.soulmagic.client.gui.GuiKnowledge;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenSimpleGui implements IMessage, IMessageHandler<MessageOpenSimpleGui, IMessage>  {
	
	private int guiCode;
	
	public MessageOpenSimpleGui() {}
	
	public MessageOpenSimpleGui(int guiCode) {
		this.guiCode = guiCode;
	}

	@Override
	public IMessage onMessage(MessageOpenSimpleGui msg, MessageContext ctx) {
		switch(msg.guiCode) {
		case Ref.Guis.KNOWLEDGE: // Relic:knowledge
			FMLClientHandler.instance().getClient().displayGuiScreen(new GuiKnowledge());
			break;
		}
		
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		guiCode = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(guiCode);
	}

}
