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
import net.minecraft.world.World;

import java.util.*;

public class PowerNetwork implements INetwork
{
    private final HashSet<INetworkPart>   parts      = new HashSet<INetworkPart>();
    private final HashSet<IConductor>     conductors = new HashSet<IConductor>();
    private final HashSet<IPowerConsumer> consumers  = new HashSet<IPowerConsumer>();
    private final HashSet<IPowerProvider> providers  = new HashSet<IPowerProvider>();

    private       SINumber voltage;
    private final World    world;

    public PowerNetwork(World world)
    {
        this.voltage = new SINumber(TheMetricSystem.Unit.VOLTAGE, 0);
        this.world = world;
        NetworkTicker.INSTANCE.addNetwork(this);
    }

    @Override
    public Set<INetworkPart> getParts()
    {
        return parts;
    }

    @Override
    public Set<IConductor> getConductors()
    {
        return conductors;
    }

    @Override
    public Set<IPowerConsumer> getPowerConsumers()
    {
        return consumers;
    }

    @Override
    public Set<IPowerProvider> getPowerProviders()
    {
        return providers;
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
        if (network != null && network != this)
        {
            PowerNetwork newNetwork = new PowerNetwork(world);
            newNetwork.getParts().addAll(this.getParts());
            newNetwork.getParts().addAll(network.getParts());

            newNetwork.refresh();
        }
    }

    @Override
    public void remove(INetworkPart part)
    {
        parts.remove(part);
        conductors.remove(part);
        providers.remove(part);
        consumers.remove(part);

        if (parts.isEmpty())
            NetworkTicker.INSTANCE.removeNetwork(this);
    }

    @Override
    public void refresh()
    {
        if (world.isRemote) return;
        System.out.println("Refresh " + parts.size());
        conductors.clear();
        providers.clear();
        consumers.clear();

        HashSet<INetworkPart> tempParts = new HashSet<INetworkPart>();
        Iterator<INetworkPart> it = parts.iterator();

        while (it.hasNext())
        {
            INetworkPart part = it.next();

            if (part == null) it.remove();
            else if (part.getTE().isInvalid()) it.remove();
            else
            {
                tempParts.add(part);
                for (INetworkPart part1 : part.getAdjacentParts())
                {
                    if (part1 != null)
                    {
                        tempParts.add(part1);
                    }
                }
            }
        }

        parts.clear();
        addAll(tempParts);

        for (IPowerProvider provider : providers)
        {
            if (!provider.getMaxPower().equals(voltage))
            {
                if (voltage == null) voltage = provider.getVoltage().clone();
                else if (provider.getMaxPower().getValue() > voltage.getValue())
                    voltage = provider.getVoltage().clone();
            }
        }

        if (parts.isEmpty())
            NetworkTicker.INSTANCE.removeNetwork(this);
    }

    public void addAll(Collection<INetworkPart> partsToAdd)
    {
        for (INetworkPart part : partsToAdd)
        {
            world.setBlock(part.getTE().xCoord, part.getTE().yCoord + 5, part.getTE().zCoord, Block.glowStone.blockID);
            add(part);
        }
    }

    @Override
    public void add(INetworkPart part)
    {
        parts.add(part);
        part.setNetwork(this);
        if (part instanceof IConductor) conductors.add((IConductor) part);
        if (part instanceof IPowerProvider) providers.add((IPowerProvider) part);
        if (part instanceof IPowerConsumer) consumers.add((IPowerConsumer) part);
    }

    public void tick()
    {
        SINumber maxProvided = new SINumber(TheMetricSystem.Unit.POWER, 0);
        ArrayList<IPowerProvider> usefullProviders = new ArrayList<IPowerProvider>();
        for (IPowerProvider provider : providers)
        {
            if (provider.getVoltage().equals(getVoltage()))
            {
                usefullProviders.add(provider);
            }
        }
        Collections.sort(usefullProviders);
        ArrayList<IPowerConsumer> requests = new ArrayList<IPowerConsumer>();
        requests.addAll(consumers);
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
}
