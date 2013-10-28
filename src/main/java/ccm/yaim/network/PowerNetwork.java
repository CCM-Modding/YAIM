package ccm.yaim.network;

import ccm.yaim.parts.IConductor;
import ccm.yaim.parts.INetworkPart;
import ccm.yaim.parts.IPowerConsumer;
import ccm.yaim.parts.IPowerProvider;
import ccm.yaim.util.SINumber;
import codechicken.lib.vec.BlockCoord;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

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
        this.world = world;
    }

    @Override
    public void tick()
    {

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
        return voltage;
    }

    @Override
    public World getWorld()
    {
        return world;
    }

    @Override
    public void refresh(INetworkPart part)
    {
        conductors.clear();
        providers.clear();
        consumers.clear();

        parts.add(part);
        checkNeighbours(new BlockCoord(part.getTE()));

        for (INetworkPart part1 : parts)
        {
            part1.setNetwork(this);
            if (part1 instanceof IConductor) conductors.add((IConductor) part1);
            if (part1 instanceof IPowerProvider) providers.add((IPowerProvider) part1);
            if (part1 instanceof IPowerConsumer) consumers.add((IPowerConsumer) part1);
        }

        for (IPowerProvider provider : providers)
        {
            if (!provider.getMaxPower().equals(voltage))
            {
                if (voltage == null) voltage = provider.getVoltage().clone();
                else if (provider.getMaxPower().getValue() > voltage.getValue())
                    voltage = provider.getVoltage().clone();
            }
        }
    }

    private void checkNeighbours(BlockCoord coord)
    {
        for (int side = 0; side < 6; side++)
        {
            BlockCoord coord1 = coord.copy().offset(side);

            TileEntity te = world.getBlockTileEntity(coord1.x, coord1.y, coord1.z);

            if (te instanceof INetworkPart)
            {
                if (!parts.contains(te))
                {
                    parts.add((INetworkPart) te);
                    world.setBlock(coord1.x, coord1.y + 10, coord1.z, Block.glowStone.blockID); //TODO: DEBUG LINE
                    checkNeighbours(coord1);
                }
            }
        }
    }
}
