package ccm.yaim.tiles;

import ccm.yaim.network.INetwork;
import ccm.yaim.parts.IConductor;
import ccm.yaim.util.SINumber;
import net.minecraft.world.World;

import static ccm.yaim.util.TheMetricSystem.Prefix.YOTTA;
import static ccm.yaim.util.TheMetricSystem.Unit.CURRENT;
import static ccm.yaim.util.TheMetricSystem.Unit.RESISTANCE;

public class TileCable extends TileNetworkPart implements IConductor
{
    private INetwork network;

    public TileCable() {super();}

    public TileCable(World world)
    {
        super(world);
    }

    @Override
    public SINumber getResistance()
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
        System.out.println(toString() + " would like to melt. I don't think so!");
    }
}
