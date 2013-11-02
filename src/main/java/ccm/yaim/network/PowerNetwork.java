package ccm.yaim.network;

import ccm.yaim.parts.IConductor;
import ccm.yaim.parts.INetworkPart;
import ccm.yaim.parts.IPowerConsumer;
import ccm.yaim.parts.IPowerProvider;
import ccm.yaim.util.ElectricHelper;
import ccm.yaim.util.SINumber;
import ccm.yaim.util.TheMetricSystem;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.TreeMultimap;
import net.minecraft.block.Block;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.*;

public class PowerNetwork implements INetwork
{
    private final HashSet<INetworkPart> parts  = new HashSet<INetworkPart>();
    //private final HashSet<IConductor>     conductors = new HashSet<IConductor>();
    //private final HashSet<IPowerConsumer> consumers  = new HashSet<IPowerConsumer>();
    //private final HashSet<IPowerProvider> providers  = new HashSet<IPowerProvider>();

    private       SINumber voltage;
    private final World    world;

    public PowerNetwork(World world)
    {
        this.voltage = new SINumber(TheMetricSystem.Unit.VOLTAGE, 0);
        this.world = world;
        NetworkTicker.INSTANCE.addNetwork(this);
    }

    public SINumber getVoltage()
    {
        return voltage.clone();
    }

    @Override
    public World getWorld()
    {
        return world;
    }

    @Override
    public void merge(INetwork network)
    {
        if (world.isRemote) return;
        if (network != null && network != this)
        {
            PowerNetwork newNetwork = new PowerNetwork(world);
            newNetwork.getParts().addAll(this.getParts());
            newNetwork.getParts().addAll(network.getParts());

            newNetwork.refresh();

            this.getParts();
            network.clear();
        }
    }

    @Override
    public void remove(INetworkPart part)
    {
        if (world.isRemote) return;
        parts.remove(part);

        if (isEmpty())
            NetworkTicker.INSTANCE.removeNetwork(this);
    }

    @Override
    public Set<INetworkPart> getParts()
    {
        return parts;
    }

    @Override
    public void refresh(INetworkPart... ignoreTiles)
    {
        if (world.isRemote) return;

        Iterator<INetworkPart> it = parts.iterator();
        List<INetworkPart> ignoreList = Arrays.asList(ignoreTiles);
        boolean done = false;
        while (!done)
        {
            HashSet<INetworkPart> tempParts = new HashSet<INetworkPart>();
            it = parts.iterator();

            while (it.hasNext())
            {
                INetworkPart part = it.next();

                if (part == null) it.remove();
                else if (part.getTE().isInvalid()) it.remove();
                else
                {
                    part.setNetwork(this);
                    for (INetworkPart part1 : part.getAdjacentParts())
                    {
                        if (part1 != null && !parts.contains(part1) && !ignoreList.contains(part1))
                        {
                            part.setNetwork(this);
                            tempParts.add(part1);
                        }
                    }
                }
            }
            addAll(tempParts);
            if (tempParts.isEmpty()) done = true;
        }

        for (INetworkPart part : parts)
        {
            if (part instanceof IPowerProvider)
            {
                IPowerProvider provider = (IPowerProvider) part;
                if (!provider.getMaxPower().equals(voltage))
                {
                    if (voltage == null) voltage = provider.getVoltage().clone();
                    else if (provider.getMaxPower().getValue() > voltage.getValue())
                        voltage = provider.getVoltage().clone();
                }
            }
        }
    }

    public void addAll(Collection<INetworkPart> partsToAdd)
    {
        if (world.isRemote) return;
        for (INetworkPart part : partsToAdd)
            add(part);
    }

    @Override
    public void add(INetworkPart part)
    {
        if (world.isRemote) return;
        part.setNetwork(this);
        parts.add(part);
    }

    public void tick()
    {
        if (world.isRemote) return;
        System.out.println("Tick " + this.hashCode());

        ArrayList<IPowerProvider> usefullProviders = new ArrayList<IPowerProvider>();
        ArrayList<IPowerConsumer> requests = new ArrayList<IPowerConsumer>();
        for (INetworkPart part : parts)
        {
            if (part instanceof IPowerProvider)
            {
                if (((IPowerProvider)part).getVoltage().equals(getVoltage()))
                {
                    usefullProviders.add((IPowerProvider)part);
                }
            }
            if (part instanceof IPowerConsumer)
            {
                requests.add((IPowerConsumer) part);
            }
        }
        Collections.sort(usefullProviders);
        Collections.sort(requests);

        Iterator<IPowerProvider> providerIterator = usefullProviders.iterator();
        Iterator<IPowerConsumer> requestIterator = requests.iterator();

        if (!providerIterator.hasNext()) return;
        IPowerProvider provider = providerIterator.next();
        SINumber maxPower = provider.getMaxPower().clone();
        float powerLeftInProvider = maxPower.getValue();

        while (requestIterator.hasNext())
        {
            IPowerConsumer request = requestIterator.next();
            SINumber requestedPower = request.getPowerRequirement().clone();
            float powerLeftInReqest = requestedPower.getValue();
            while (powerLeftInReqest > 0)
            {
                if (powerLeftInReqest <= powerLeftInProvider)
                {
                    powerLeftInProvider -= powerLeftInReqest;
                    powerLeftInReqest = 0;
                }
                else
                {
                    powerLeftInReqest -= powerLeftInProvider;
                    provider.providePower(maxPower);

                    if (!providerIterator.hasNext()) break;
                    provider = providerIterator.next();
                    powerLeftInProvider = provider.getMaxPower().getValue();
                }
            }

            request.usePower(requestedPower.subtract(powerLeftInReqest));
        }

        if (powerLeftInProvider != maxPower.getValue())
            provider.providePower(maxPower.subtract(powerLeftInProvider));
    }

    @Override
    public boolean isEmpty()
    {
        return parts.isEmpty();
    }

    @Override
    public void clear()
    {
        parts.clear();
        NetworkTicker.INSTANCE.removeNetwork(this);
    }

    @Override
    public void split(INetworkPart part)
    {
        remove(part);

        for (INetworkPart part1 : part.getAdjacentParts())
        {
            if (part1 != null)
            {
                part1.getNetwork().clear();
                part1.setNetwork(null);
                part1.getNetwork().refresh(part);
            }
        }
    }

    @Override
    public String toString()
    {
        return "PownerNet with P:" + parts.size() + " hc:" + this.hashCode() + " Server?:" + !world.isRemote;
    }
}
