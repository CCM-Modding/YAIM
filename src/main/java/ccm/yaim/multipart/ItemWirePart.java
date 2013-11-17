package ccm.yaim.multipart;

import ccm.yaim.model.WireSmall;
import ccm.yaim.util.ElectricHelper;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.JItemMultiPart;
import codechicken.multipart.TMultiPart;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

public class ItemWirePart extends JItemMultiPart
{
    public ItemWirePart(int id)
    {
        super(id);
        this.setCreativeTab(CreativeTabs.tabRedstone);

        MinecraftForgeClient.registerItemRenderer(this.itemID, new ItemWirePartRenderer());
    }

    @Override
    public TMultiPart newPart(ItemStack item, EntityPlayer player, World world, BlockCoord pos, int side, Vector3 vhit)
    {
        return ElectricHelper.containsCable(world.getBlockTileEntity(pos.x, pos.y, pos.z)) ? null : new CablePart();
    }

    public static class ItemWirePartRenderer implements IItemRenderer
    {
        @Override
        public boolean handleRenderType(ItemStack item, ItemRenderType type)
        {
            return true;
        }

        @Override
        public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
        {
            return true;
        }

        @Override
        public void renderItem(ItemRenderType type, ItemStack item, Object... data)
        {
            switch (type)
            {
                case ENTITY:
                    renderWire(item, -.5f, 0f, -.5f, 1f);
                    break;
                case EQUIPPED:
                    renderWire(item, 0f, .0f, 0f, 1f);
                    break;
                case EQUIPPED_FIRST_PERSON:
                    renderWire(item, 1f, -.6f, -.4f, 2f);
                    break;
                case INVENTORY:
                    renderWire(item, 0f, -.1f, 0f, 1f);
                    break;
            }
        }

        public void renderWire(ItemStack stack, float x, float y, float z, float scale)
        {
            GL11.glPushMatrix();
            Minecraft.getMinecraft().renderEngine.bindTexture(WireSmall.INSTANCE.getTexture());
            GL11.glTranslatef(x + 0.5F, y + 1.5F, z + 0.5F);
            GL11.glScalef(scale, scale, scale);
            GL11.glScalef(1.0F, -1F, -1F);
            WireSmall.INSTANCE.renderAll();
            GL11.glPopMatrix();
        }
    }
}
