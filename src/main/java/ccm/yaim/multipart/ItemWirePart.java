package ccm.yaim.multipart;

import ccm.yaim.util.ElectricHelper;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.JItemMultiPart;
import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.TMultiPart;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemWirePart extends JItemMultiPart
{
    public ItemWirePart(int id)
    {
        super(id);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    @Override
    public TMultiPart newPart(ItemStack item, EntityPlayer player, World world, BlockCoord pos, int side, Vector3 vhit)
    {
        return ElectricHelper.containsCable(world.getBlockTileEntity(pos.x, pos.y, pos.z)) ? null : new CablePart();
    }
}
