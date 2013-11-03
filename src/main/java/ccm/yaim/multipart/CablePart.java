package ccm.yaim.multipart;

import ccm.yaim.YAIM;
import codechicken.lib.vec.Cuboid6;
import codechicken.multipart.JCuboidPart;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.minecraft.McBlockPart;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;

public class CablePart extends McBlockPart
{
    /**
     * The unique string identifier for this class of multipart.
     */
    @Override
    public String getType()
    {
        return Content.cableid;
    }

    /**
     * Return the bounding Cuboid6 for this part.
     */
    @Override
    public Cuboid6 getBounds()
    {
        return new Cuboid6(0.4, 0.4, 0.4, 0.6, 0.6, 0.6);
    }

    @Override
    public Block getBlock()
    {
        return YAIM.instance.blockCable;
    }

    @Override
    public boolean activate(EntityPlayer player, MovingObjectPosition hit, ItemStack itemStack)
    {
        YAIM.instance.blockCable.onBlockActivated(this.world(), this.x(), this.y(), this.z(), player);
        return false;
    }
}
