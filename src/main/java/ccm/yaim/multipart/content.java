package ccm.yaim.multipart;

import ccm.yaim.YAIM;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import net.minecraft.world.World;

public class Content implements MultiPartRegistry.IPartFactory
{
    public static String cableid = "yaim_cable";

    public void init()
    {
        MultiPartRegistry.registerParts(this, new String[]{ cableid });
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
