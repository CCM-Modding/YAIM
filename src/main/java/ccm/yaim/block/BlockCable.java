package ccm.yaim.block;

import ccm.yaim.tiles.TileCable;
import ccm.yaim.util.Data;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCable extends BlockNetworkPart
{
    public BlockCable(int par1, Material par2Material)
    {
        super(par1, par2Material);
        this.setUnlocalizedName("cable");
        this.setTextureName(Data.MODID + ":cable");
        this.setBlockBounds(0.4f, 0.4f, 0.4f, 0.6f, 0.6f, 0.6f);
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileCable(world);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean isBlockNormalCube(World world, int x, int y, int z)
    {
        return false;
    }
}
