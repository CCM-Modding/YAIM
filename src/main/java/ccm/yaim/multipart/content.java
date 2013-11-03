package ccm.yaim.multipart;

import ccm.yaim.YAIM;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import net.minecraft.world.World;

public class Content implements MultiPartRegistry.IPartFactory, MultiPartRegistry.IPartConverter
{
    public static String cableid = "yaim_cable";

    public void init()
    {
        MultiPartRegistry.registerConverter(this);
        MultiPartRegistry.registerParts(this, new String[]{ cableid });
    }

    /**
     * Return true if this converter can handle the specific blockID (may or may not actually convert the block)
     */
    @Override
    public boolean canConvert(int blockID)
    {
        return blockID == YAIM.instance.blockCable.blockID;
    }

    /**
     * Return a multipart version of the block at pos in world. Return null if no conversion is possible.
     */
    @Override
    public TMultiPart convert(World world, BlockCoord pos)
    {
        int id = world.getBlockId(pos.x, pos.y, pos.z);

        if (id == YAIM.instance.blockCable.blockID)
            return new CablePart();

        return null;
    }

    /**
     * Create a new instance of the part with the specified type name identifier
     * @param client If the part instance is for the client or the server
     */
    @Override
    public TMultiPart createPart(String name, boolean client)
    {
        if (name.equals(cableid)) return new CablePart();

        return null;
    }
}
