package ccm.yaim.tiles;

import ccm.yaim.network.INetwork;
import ccm.yaim.network.PowerNetwork;
import ccm.yaim.parts.IConductor;
import ccm.yaim.parts.INetworkPart;
import ccm.yaim.util.ElectricHelper;
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
    public INetworkPart[] getAdjacentParts()
    {
        return ElectricHelper.getAdjacentParts(worldObj, new BlockCoord(this));
    }

    @Override
    public void debug(World world, int x, int y, int z, EntityPlayer entityPlayer)
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
