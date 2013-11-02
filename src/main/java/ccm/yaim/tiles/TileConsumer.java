package ccm.yaim.tiles;

import ccm.yaim.network.INetwork;
import ccm.yaim.parts.IPowerConsumer;
import ccm.yaim.util.SINumber;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.world.World;

import static ccm.yaim.util.TheMetricSystem.Unit.POWER;
import static ccm.yaim.util.TheMetricSystem.Unit.RESISTANCE;

public class TileConsumer extends TileNetworkPart implements IPowerConsumer
{
    private INetwork network;
    SINumber powerconsumed = new SINumber(POWER, 0);

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
        powerconsumed = powerconsumed.add(power.getValue());
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

    @Override
    public void debug(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote) return;
        entityPlayer.sendChatToPlayer(ChatMessageComponent.createFromText("Consumed " + powerconsumed.toString()));
    }
}
