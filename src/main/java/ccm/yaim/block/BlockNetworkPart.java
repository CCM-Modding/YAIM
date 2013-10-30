package ccm.yaim.block;

import ccm.yaim.parts.INetworkPart;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class BlockNetworkPart extends BlockContainer
{
    protected BlockNetworkPart(int par1, Material par2Material)
    {
        super(par1, par2Material);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);

        TileEntity myTe = world.getBlockTileEntity(x, y, z);

        if (myTe instanceof INetworkPart)
        {
            INetworkPart tile = (INetworkPart) myTe;
            tile.refresh();
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockID)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof INetworkPart)
        {
            ((INetworkPart) tileEntity).refresh();
        }
    }
}

