package ccm.yaim.tiles;

import ccm.yaim.network.INetwork;
import ccm.yaim.parts.IPowerConsumer;
import ccm.yaim.util.SINumber;
import net.minecraft.world.World;

import static ccm.yaim.util.TheMetricSystem.Unit.POWER;
import static ccm.yaim.util.TheMetricSystem.Unit.RESISTANCE;

public class TileConsumer extends TileNetworkPart implements IPowerConsumer
{
    private INetwork network;

    public TileConsumer()
    {
        super();
    }

    public TileConsumer(World world)
    {
        super(world);
    }

    @Override
    public void usePower(SINumber power)
    {
        System.out.println(this.toString() + " consumed " + power.toString());
    }

    @Override
    public SINumber getPowerRequirement()
    {
        return new SINumber(POWER, 100);
    }

    @Override
    public byte getConsumerPriority()
    {
        return Byte.MIN_VALUE;
    }

    @Override
    public int compareTo(IPowerConsumer o)
    {
        return getConsumerPriority() - o.getConsumerPriority();
    }
}
