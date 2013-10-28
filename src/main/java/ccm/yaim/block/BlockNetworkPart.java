package ccm.yaim.block;

import ccm.yaim.network.PowerNetwork;
import ccm.yaim.parts.INetworkPart;
import ccm.yaim.tiles.TileNetworkPart;
import codechicken.lib.vec.BlockCoord;
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

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof TileNetworkPart)
        {
            TileNetworkPart tile = (TileNetworkPart) tileEntity;

            boolean flag = false;
            for (int side = 0; side < 6; side++)
            {
                BlockCoord blockCoord = new BlockCoord(tile).offset(side);

                TileEntity te = world.getBlockTileEntity(blockCoord.x, blockCoord.y, blockCoord.z);

                if (te instanceof INetworkPart)
                {
                    flag = true;
                    ((INetworkPart) te).getNetwork().refresh(tile);
                    break;
                }
            }

            if (!flag)
            {
                System.out.println("New network"); //TODO: DEBUG LINE
                tile.setNetwork(new PowerNetwork(world));
                tile.getNetwork().refresh(tile);
            }
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int oldId, int oldMeta)
    {
        super.breakBlock(world, x, y, z, oldId, oldMeta);

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (tileEntity instanceof TileNetworkPart)
        {
            TileNetworkPart tile = (TileNetworkPart) tileEntity;

            tile.getNetwork().refresh(tile);
        }
    }
}

