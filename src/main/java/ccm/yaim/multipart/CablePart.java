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
import codechicken.lib.vec.Rotation;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.IHollowConnect;
import codechicken.multipart.JNormalOcclusion;
import codechicken.multipart.NormalOcclusionTest;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static ccm.yaim.util.TheMetricSystem.Prefix.YOTTA;
import static ccm.yaim.util.TheMetricSystem.Unit.CURRENT;
import static ccm.yaim.util.TheMetricSystem.Unit.RESISTANCE;

public class CablePart extends TMultiPart implements IConductor, IHollowConnect, JNormalOcclusion
{
    private static int       expandBounds  = -1;
    public static  Cuboid6[] boundingBoxes = new Cuboid6[7];
    private INetwork network;
    boolean[] connectionMap = new boolean[6];

    static
    {
        double w = 0.1;
        boundingBoxes[6] = new Cuboid6(0.5 - w, 0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w, 0.5 + w);
        for (int s = 0; s < 6; s++)
        {
            boundingBoxes[s] = new Cuboid6(0.5 - w, 0, 0.5 - w, 0.5 + w, 0.5 - w, 0.5 + w).apply(Rotation.sideRotations[s].at(Vector3.center));
        }
    }

    @Override
    public Iterable<Cuboid6> getOcclusionBoxes()
    {
        try
        {
            if (expandBounds != -1) return Arrays.asList(boundingBoxes[expandBounds]);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("[YAIM] dafuq??");
        }

        return Arrays.asList(boundingBoxes[6]);
    }

    @Override
    public Iterable<Cuboid6> getCollisionBoxes()
    {
        LinkedList<Cuboid6> list = new LinkedList<Cuboid6>();
        list.add(boundingBoxes[6]);
        for (int s = 0; s < 6; s++)
        {
            if (connectionMap[s]) list.add(boundingBoxes[s]);
        }
        return list;
    }

    @Override
    public Iterable<IndexedCuboid6> getSubParts()
    {
        Iterable<Cuboid6> boxList = getCollisionBoxes();
        LinkedList<IndexedCuboid6> partList = new LinkedList<IndexedCuboid6>();
        for (Cuboid6 c : boxList)
        { partList.add(new IndexedCuboid6(0, c)); }
        return partList;
    }

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
        for (int i = 0; i < 6; i++) if (connectionMap[i]) WireSmall.INSTANCE.renderSide(ForgeDirection.getOrientation(i));
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
    public boolean occlusionTest(TMultiPart npart)
    {
        return NormalOcclusionTest.apply(this, npart);
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
        INetworkPart[] adjacentParts = new INetworkPart[6];
        for (int s = 0; s < 6; s++)
        { adjacentParts[s] = connectionPossible(s) && connectionMade(s) ? ElectricHelper.getPartOnSide(world(), x(), y(), z(), s) : null; }
        return adjacentParts;
    }

    @Override
    public void debug(World world, int x, int y, int z, EntityPlayer entityPlayer)
    {
        entityPlayer.addChatMessage(getNetwork().toString());
        for (INetworkPart part : getAdjacentParts())
        {
            if (part == null) entityPlayer.addChatMessage("null");
            else entityPlayer.addChatMessage(part.toString());
        }
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
    public void onPartChanged(TMultiPart part)
    {
        this.getNetwork().clear();
        this.setNetwork(new PowerNetwork(world()));
        this.getNetwork().add(this);
        this.getNetwork().refresh();
        update();
        for (int i = 0; i < 6; i++)
        {
            INetworkPart part1 = ElectricHelper.getPartOnSide(world(), x(), y(), z(), i);
            if (part1 != null)
            {
                part1.setNetwork(new PowerNetwork(world()));
                part1.getNetwork().add(part1);
                part1.getNetwork().refresh();
            }
        }
    }

    @Override
    public void update()
    {
        for (int s = 0; s < 6; s++)
        {
            connectionMap[s] = connectionPossible(s) && connectionMade(s);
        }
        for (INetworkPart part : getAdjacentParts())
        {
            if (part != null)
            {
                getNetwork().merge(part.getNetwork());
            }
        }
    }

    public boolean connectionMade(int s)
    {
        BlockCoord pos = new BlockCoord(tile()).offset(s);
        TileEntity te = world().getBlockTileEntity(pos.x, pos.y, pos.z);
        if (te != null)
        {
            if (te instanceof TileMultipart)
            {
                for (TMultiPart tp : ((TileMultipart) te).jPartList())
                {
                    if (tp instanceof CablePart)
                    {
                        return ((CablePart) tp).canConnect(s ^ 1);
                    }
                }
            }
            else if (te instanceof INetworkPart)
            {
                return true;
            }
        }
        return false;
    }

    private boolean canConnect(int i)
    {
        return connectionPossible(i);
    }

    public boolean connectionPossible(int s)
    {
        TMultiPart facePart = tile().partMap(s);
        if (facePart == null) return true;

        expandBounds = s;
        boolean fits = tile().canReplacePart(this, this);
        expandBounds = -1;

        return fits;
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

    public boolean activate(EntityPlayer player, MovingObjectPosition hit, ItemStack item)
    {
        if (world().isRemote) return false;
        if (item.getItem().itemID == Item.stick.itemID && !player.isSneaking())
        {
            debug(player.getEntityWorld(), hit.blockX, hit.blockY, hit.blockZ, player);
            return true;
        }
        return false;
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
