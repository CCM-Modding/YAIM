package ccm.yaim.network;

import ccm.yaim.util.Data;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import java.util.EnumSet;
import java.util.HashSet;

public class NetworkTicker implements ITickHandler
{
    public static final NetworkTicker INSTANCE = new NetworkTicker();
    private HashSet<INetwork> networks = new HashSet<INetwork>();
    private HashSet<INetwork> add = new HashSet<INetwork>();
    private HashSet<INetwork> del = new HashSet<INetwork>();

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        networks.removeAll(del) ; del.clear();
        networks.addAll(add)    ; add.clear();
        for (INetwork network : networks)
        {
            if (network.getWorld().equals(tickData[0]))
                network.tick();
        }
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return EnumSet.of(TickType.WORLD);
    }

    @Override
    public String getLabel()
    {
        return Data.MODID + "_" + getClass().getSimpleName();
    }

    public void addNetwork(INetwork network)
    {
        System.out.println("Adding network");
        add.add(network);
    }

    public void removeNetwork(INetwork network)
    {
        System.out.println("Removing network");
        del.add(network);
    }

    public HashSet<INetwork> getNetworks()
    {
        return networks;
    }
}
