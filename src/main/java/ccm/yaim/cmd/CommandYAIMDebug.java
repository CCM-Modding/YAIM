package ccm.yaim.cmd;

import ccm.yaim.network.INetwork;
import ccm.yaim.network.NetworkTicker;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

import java.util.List;

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
        if (args.length == 0 || args[0].equalsIgnoreCase("list"))
        {
            sender.sendChatToPlayer(ChatMessageComponent.createFromText("Networks: " + NetworkTicker.INSTANCE.getNetworks().size()));
            for (INetwork network : NetworkTicker.INSTANCE.getNetworks())
            {
                sender.sendChatToPlayer(ChatMessageComponent.createFromText(network.toString()));
            }
        }
        else if (args[0].equalsIgnoreCase("refresh"))
        {
            int old = NetworkTicker.INSTANCE.getNetworks().size();
            for (INetwork network : NetworkTicker.INSTANCE.getNetworks())
            {
                network.refresh();
            }
            sender.sendChatToPlayer(ChatMessageComponent.createFromText("Old size: " + old + ", new size: " + NetworkTicker.INSTANCE.getNetworks().size()));
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args)
    {
        if (args.length == 1) return getListOfStringsMatchingLastWord(args, "list", "refresh");
        return null;
    }
}
