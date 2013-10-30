package ccm.yaim.tiles;

import ccm.yaim.network.INetwork;
import net.minecraft.world.World;

public class TileCable extends TileNetworkPart
{
    private INetwork network;

    public TileCable()
    {
        super();
    }

    public TileCable(World world)
    {
        super(world);
    }

    @Override
    public boolean canUpdate()
    {
        return false;
    }
}
