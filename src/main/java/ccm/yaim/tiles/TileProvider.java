package ccm.yaim.tiles;

import ccm.yaim.network.INetwork;
import ccm.yaim.parts.IPowerProvider;
import ccm.yaim.util.SINumber;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;

import static ccm.yaim.util.TheMetricSystem.Prefix.KILO;
import static ccm.yaim.util.TheMetricSystem.Prefix.YOTTA;
import static ccm.yaim.util.TheMetricSystem.Unit.POWER;
import static ccm.yaim.util.TheMetricSystem.Unit.VOLTAGE;

public class TileProvider extends TileNetworkPart implements IPowerProvider
{
    private INetwork network;
    SINumber powerprovided = new SINumber(POWER, 0);

    public TileProvider()
    {
        super();
    }

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
        return new SINumber(POWER, 1, KILO);
    }

    @Override
    public void providePower(SINumber power)
    {
        powerprovided = powerprovided.add(power.getValue());
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

    @Override
    public void debug(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote) return;
        entityPlayer.sendChatToPlayer(ChatMessageComponent.createFromText("Provided " + powerprovided.toString()));
    }
}
