package ccm.yaim.tiles;

import ccm.yaim.network.INetwork;
import ccm.yaim.parts.IPowerProvider;
import ccm.yaim.util.SINumber;
import net.minecraft.world.World;

import static ccm.yaim.util.TheMetricSystem.Prefix.YOTTA;
import static ccm.yaim.util.TheMetricSystem.Unit.POWER;
import static ccm.yaim.util.TheMetricSystem.Unit.VOLTAGE;

public class TileProvider extends TileNetworkPart implements IPowerProvider
{
    private INetwork network;

    public TileProvider() { super();}

    public TileProvider(World world)
    {
        super(world);
    }

    @Override
    public SINumber getVoltage()
    {
        return new SINumber(VOLTAGE, 250);
    }

    @Override
    public SINumber getMaxPower()
    {
        return new SINumber(POWER, 1, YOTTA);
    }

    @Override
    public void providePower(SINumber amps)
    {
        System.out.println(this.toString() + " provided " + amps.toString());
    }

    @Override
    public byte getProviderPriority()
    {
        return Byte.MAX_VALUE;
    }

    @Override
    public int compareTo(IPowerProvider o)
    {
        return getProviderPriority() - o.getProviderPriority();
    }
}
