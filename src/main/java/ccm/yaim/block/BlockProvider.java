package ccm.yaim.block;

import ccm.yaim.tiles.TileProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockProvider extends BlockNetworkPart
{
    public BlockProvider(int par1, Material par2Material)
    {
        super(par1, par2Material);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileProvider(world);
    }
}
