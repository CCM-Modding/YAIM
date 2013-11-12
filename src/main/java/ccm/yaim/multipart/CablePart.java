package ccm.yaim.multipart;

import ccm.yaim.YAIM;
import ccm.yaim.model.WireSmall;
import ccm.yaim.network.INetwork;
import ccm.yaim.network.PowerNetwork;
import ccm.yaim.parts.IConductor;
import ccm.yaim.parts.INetworkPart;
import ccm.yaim.util.ElectricHelper;
import ccm.yaim.util.SINumber;
import codechicken.lib.lighting.LazyLightMatrix;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.IHollowConnect;
import codechicken.multipart.TMultiPart;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ccm.yaim.util.TheMetricSystem.Prefix.YOTTA;
import static ccm.yaim.util.TheMetricSystem.Unit.CURRENT;
import static ccm.yaim.util.TheMetricSystem.Unit.RESISTANCE;

public class CablePart extends TMultiPart implements IConductor, IHollowConnect
{
    private INetwork network;
    INetworkPart[] adjacentParts;

    @Override
    @SideOnly(Side.CLIENT)
    public void renderStatic(Vector3 pos, LazyLightMatrix olm, int pass)
    {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderDynamic(Vector3 pos, float frame, int pass)
    {
        GL11.glPushMatrix();
        Minecraft.getMinecraft().renderEngine.bindTexture(WireSmall.INSTANCE.getTexture());
        GL11.glTranslatef((float) pos.x + 0.5F, (float) pos.y + 1.5F, (float) pos.z + 0.5F);
        GL11.glScalef(1.0F, -1F, -1F);
        WireSmall.INSTANCE.renderCenter();
        INetworkPart[] networkParts = this.getAdjacentParts();
        for (int i = 0; i < 6; i++) if (networkParts[i] != null) WireSmall.INSTANCE.renderSide(ForgeDirection.getOrientation(i));
        GL11.glPopMatrix();
    }

    /**
     * The unique string identifier for this class of multipart.
     */
    @Override
    public String getType()
    {
        return Content.cableid;
    }

    @Override
    public List<Cuboid6> getCollisionBoxes()
    {
        double size = 0.4;
        ArrayList<Cuboid6> list = new ArrayList<Cuboid6>();
        INetworkPart[] networkParts = this.getAdjacentParts();
        list.add(new Cuboid6(size, size, size, 1 - size, 1 - size, 1 - size));
        if (networkParts[0] != null) list.add(new Cuboid6(size, 0, size, 1 - size, 1 - size, 1 - size));
        if (networkParts[1] != null) list.add(new Cuboid6(size, size, size, 1 - size, 1, 1 - size));
        if (networkParts[2] != null) list.add(new Cuboid6(size, size, 0, 1 - size, 1 - size, 1 - size));
        if (networkParts[3] != null) list.add(new Cuboid6(size, size, size, 1 - size, 1 - size, 1));
        if (networkParts[4] != null) list.add(new Cuboid6(0, size, size, 1 - size, 1 - size, 1 - size));
        if (networkParts[5] != null) list.add(new Cuboid6(size, size, size, 1, 1 - size, 1 - size));

        return list;
    }

    @Override
    public List<IndexedCuboid6> getSubParts()
    {
        double size = 0.4;
        INetworkPart[] networkParts = this.getAdjacentParts();
        ArrayList<IndexedCuboid6> list = new ArrayList<IndexedCuboid6>();

        list.add(new IndexedCuboid6(0, new Cuboid6(size, size, size, 1 - size, 1 - size, 1 - size)));
        if (networkParts[0] != null) list.add(new IndexedCuboid6(0, new Cuboid6(size, 0, size, 1 - size, 1 - size, 1 - size)));
        if (networkParts[1] != null) list.add(new IndexedCuboid6(0, new Cuboid6(size, size, size, 1 - size, 1, 1 - size)));
        if (networkParts[2] != null) list.add(new IndexedCuboid6(0, new Cuboid6(size, size, 0, 1 - size, 1 - size, 1 - size)));
        if (networkParts[3] != null) list.add(new IndexedCuboid6(0, new Cuboid6(size, size, size, 1 - size, 1 - size, 1)));
        if (networkParts[4] != null) list.add(new IndexedCuboid6(0, new Cuboid6(0, size, size, 1 - size, 1 - size, 1 - size)));
        if (networkParts[5] != null) list.add(new IndexedCuboid6(0, new Cuboid6(size, size, size, 1, 1 - size, 1 - size)));

        return list;
    }

    @Override
    public INetwork getNetwork()
    {
        if (network == null)
        {
            network = new PowerNetwork(world());
            network.add(this);
        }
        return network;
    }

    @Override
    public List<ItemStack> getDrops()
    {
        return Arrays.asList(new ItemStack(YAIM.instance.itemWirePart, 1));
    }

    @Override
    public ItemStack pickItem(MovingObjectPosition hit)
    {
        return new ItemStack(YAIM.instance.itemWirePart, 1);
    }

    @Override
    public void setNetwork(INetwork network)
    {
        if (this.network != null && network != this.network) this.network.remove(this);
        this.network = network;
    }

    @Override
    public INetworkPart[] getAdjacentParts()
    {
        if (this.tile() == null) return new INetworkPart[6];
        if (adjacentParts == null) adjacentParts = ElectricHelper.getAdjacentParts(world(), new BlockCoord(this.x(), this.y(), this.z()));
        return adjacentParts;
    }

    @Override
    public void debug(World world, int x, int y, int z, EntityPlayer entityPlayer)
    {
        entityPlayer.sendChatToPlayer(ChatMessageComponent.createFromText(this.getNetwork().toString()));
    }

    @Override
    public void init()
    {
        update();
    }

    @Override
    public void onNeighborChanged()
    {
        update();
    }

    @Override
    public void onWorldJoin()
    {
        init();
    }

    @Override
    public void onWorldSeparate()
    {
        remove();
    }

    @Override
    public void update()
    {
        adjacentParts = null;
        for (INetworkPart part : getAdjacentParts())
        {
            if (part != null)
            {
                getNetwork().merge(part.getNetwork());
            }
        }
    }

    @Override
    public void remove()
    {
        getNetwork().split(this);
    }

    @Override
    public SINumber getResistancePerMeter()
    {
        return new SINumber(RESISTANCE, 0);
    }

    @Override
    public SINumber getMaxCurrent()
    {
        return new SINumber(CURRENT, 1, YOTTA);
    }

    @Override
    public void melt()
    {

    }

    /**
     * @return The size (width and height) of the connection in pixels. Must be be less than 12 and more than 0
     */
    @Override
    public int getHollowSize()
    {
        return 12;
    }
}
