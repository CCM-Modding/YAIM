package ccm.yaim.tiles;

import ccm.yaim.network.INetwork;
import ccm.yaim.parts.INetworkPart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileNetworkPart extends TileEntity implements INetworkPart
{
    private INetwork network;

    public TileNetworkPart(World world)
    {
        super();
        setWorldObj(world);
    }

    public TileNetworkPart()
    {

    }

    @Override
    public INetwork getNetwork()
    {
        return network;
    }

    @Override
    public void setNetwork(INetwork network)
    {
        this.network = network;
    }

    @Override
    public TileEntity getTE()
    {
        return this;
    }
}
