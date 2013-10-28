package ccm.yaim.tiles;

import ccm.yaim.network.INetwork;
import ccm.yaim.parts.IPowerConsumer;
import ccm.yaim.util.SINumber;
import net.minecraft.world.World;

import static ccm.yaim.util.TheMetricSystem.Unit.POWERFACTOR;
import static ccm.yaim.util.TheMetricSystem.Unit.RESISTANCE;

public class TileConsumer extends TileNetworkPart implements IPowerConsumer
{
    private INetwork network;

    public TileConsumer() {super();}

    public TileConsumer(World world)
    {
        super(world);
    }

    @Override
    public SINumber getResistance()
    {
        return new SINumber(RESISTANCE, 250);
    }

    @Override
    public SINumber getPowerFactor()
    {
        return new SINumber(POWERFACTOR, 1);
    }

    @Override
    public void usePower(SINumber voltage, SINumber amps)
    {
        System.out.println(this.toString() + " consumed " + amps.toString() + " at " + voltage.toString());
    }
}
