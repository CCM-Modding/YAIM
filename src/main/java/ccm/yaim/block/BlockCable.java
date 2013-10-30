package ccm.yaim.block;

import ccm.yaim.tiles.TileCable;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCable extends BlockNetworkPart
{
    public BlockCable(int par1, Material par2Material)
    {
        super(par1, par2Material);
        this.setUnlocalizedName("cable");
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileCable(world);
    }
}
