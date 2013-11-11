package ccm.yaim.parts;

import ccm.yaim.network.INetwork;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Extended by all parts of a network.
 *
 * @author Dries007
 */
public interface INetworkPart
{
    public INetwork getNetwork();

    public void setNetwork(INetwork network);

    /**
     * The array needs to be 6 long!
     *
     * @return an array of all the adjacent network parts
     */
    public INetworkPart[] getAdjacentParts();

    void debug(World world, int x, int y, int z, EntityPlayer entityPlayer);

    void init();

    void update();

    void remove();
}
