package ccm.yaim.block;

import ccm.yaim.tiles.TileConsumer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockConsumer extends BlockNetworkPart
{
    public BlockConsumer(int par1, Material par2Material)
    {
        super(par1, par2Material);
        this.setUnlocalizedName("consumer");
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileConsumer(world);
    }
}
