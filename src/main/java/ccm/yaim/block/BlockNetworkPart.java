package ccm.yaim.block;

import ccm.yaim.parts.INetworkPart;
import ccm.yaim.util.Data;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
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
        TileEntity myTe = world.getBlockTileEntity(x, y, z);

        if (myTe instanceof INetworkPart)
        {
            INetworkPart tile = (INetworkPart) myTe;
            tile.refresh();
        }
        super.onBlockAdded(world, x, y, z);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int blockID)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof INetworkPart)
        {
            ((INetworkPart) tileEntity).refresh();
        }
        super.onNeighborBlockChange(world, x, y, z, blockID);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int oldID, int oldMeta)
    {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (tileEntity instanceof INetworkPart)
        {
            ((INetworkPart) tileEntity).getNetwork().remove((INetworkPart) tileEntity);
        }
        super.breakBlock(world, x, y, z, oldID, oldMeta);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int side, float hitX, float hitY, float hitZ)
    {
        if (world.isRemote) return false;
        if (entityPlayer.getHeldItem() != null && entityPlayer.getHeldItem().itemID == Data.DEBUGITEM)
        {
            TileEntity te = world.getBlockTileEntity(x, y, z);

            if (te instanceof INetworkPart)
            {
                ((INetworkPart)te).debug(world, x, y, z, entityPlayer, side, hitX, hitY, hitZ);
            }
        }
        return true;
    }
}

