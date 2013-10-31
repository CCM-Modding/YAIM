package ccm.yaim.cmd;

import ccm.yaim.network.INetwork;
import ccm.yaim.network.NetworkTicker;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

public class CommandYAIMDebug extends CommandBase
{

    @Override
    public String getCommandName()
    {
        return "yaim";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        sender.sendChatToPlayer(ChatMessageComponent.createFromText("Networks: " + NetworkTicker.INSTANCE.getNetworks().size()));
        for (INetwork network : NetworkTicker.INSTANCE.getNetworks())
        {
            sender.sendChatToPlayer(ChatMessageComponent.createFromText(network.toString()));
        }
    }
}
