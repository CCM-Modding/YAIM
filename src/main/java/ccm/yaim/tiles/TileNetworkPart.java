package ccm.yaim.tiles;

import ccm.yaim.network.INetwork;
import ccm.yaim.network.PowerNetwork;
import ccm.yaim.parts.IConductor;
import ccm.yaim.parts.INetworkPart;
import ccm.yaim.util.SINumber;
import codechicken.lib.vec.BlockCoord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
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
        if (this.network != null && network != this.network) this.network.remove(this);
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
    public void debug(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        entityPlayer.sendChatToPlayer(ChatMessageComponent.createFromText(this.getNetwork().toString()));
    }

    @Override
    public void init()
    {
        update();
    }

    @Override
    public void update()
    {
        adjacentParts = null;
        for (INetworkPart part : getAdjacentParts())
        {
            if (part != null)
            {
                getNetwork().merge(part.getNetwork());
            }
        }
    }

    @Override
    public void remove()
    {
        getNetwork().split(this);
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
    }
}