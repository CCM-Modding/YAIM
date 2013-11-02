package ccm.yaim.network;

import ccm.yaim.util.Data;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IScheduledTickHandler;
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
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) return;
        networks.removeAll(del);
        networks.addAll(add);
        del.clear();
        add.clear();
        for (INetwork network : networks)
        {
            if (network.isEmpty()) del.add(network);
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
        if (network.getWorld().isRemote) return;
        add.add(network);
    }

    public void removeNetwork(INetwork network)
    {
        if (network.getWorld().isRemote) return;
        del.add(network);
    }

    public HashSet<INetwork> getNetworks()
    {
        return networks;
    }

    public void clear()
    {
        networks.clear();
        add.clear();
        del.clear();
    }
}
