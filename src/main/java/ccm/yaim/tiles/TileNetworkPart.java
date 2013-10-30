package ccm.yaim.tiles;

import ccm.yaim.network.INetwork;
import ccm.yaim.network.PowerNetwork;
import ccm.yaim.parts.IConductor;
import ccm.yaim.parts.INetworkPart;
import ccm.yaim.util.SINumber;
import codechicken.lib.vec.BlockCoord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static ccm.yaim.util.TheMetricSystem.Prefix.YOTTA;
import static ccm.yaim.util.TheMetricSystem.Unit.CURRENT;
import static ccm.yaim.util.TheMetricSystem.Unit.RESISTANCE;

public class TileNetworkPart extends TileEntity implements INetworkPart, IConductor
{
    private INetwork       network;
    private INetworkPart[] adjacentParts;

    public TileNetworkPart()
    {
        super();
    }

    public TileNetworkPart(World world)
    {
        super();
        setWorldObj(world);
    }

    @Override
    public INetwork getNetwork()
    {
        if (network == null)
        {
            network = new PowerNetwork(worldObj);
            network.add(this);
        }
        return network;
    }

    @Override
    public void setNetwork(INetwork network)
    {
        if (network != null && network != this.network) network.remove(this);
        this.network = network;
    }

    @Override
    public TileEntity getTE()
    {
        return this;
    }

    @Override
    public INetworkPart[] getAdjacentParts()
    {
        if (adjacentParts == null)
        {
            adjacentParts = new INetworkPart[6];

            for (int i = 0; i < 6; i++)
            {
                BlockCoord coord = new BlockCoord(this);
                coord.offset(i);
                TileEntity te = getWorldObj().getBlockTileEntity(coord.x, coord.y, coord.z);

                if (te instanceof INetworkPart)
                {
                    adjacentParts[i] = (INetworkPart) te;
                }
            }
        }

        return adjacentParts;
    }

    @Override
    public void refresh()
    {
        adjacentParts = null;

        for (INetworkPart part : getAdjacentParts())
        {
            if (part != null) this.getNetwork().merge(part.getNetwork());
        }

        this.getNetwork().refresh();
    }

    @Override
    public SINumber getResistancePerMeter()
    {
        return new SINumber(RESISTANCE, 0);
    }

    @Override
    public SINumber getMaxCurrent()
    {
        return new SINumber(CURRENT, 1, YOTTA);
    }

    @Override
    public void melt()
    {
    }

    @Override
    public void invalidate()
    {
        super.invalidate();
        getNetwork().refresh();
    }
}
