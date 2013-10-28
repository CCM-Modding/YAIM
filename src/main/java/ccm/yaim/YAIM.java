package ccm.yaim;

import ccm.yaim.block.BlockCable;
import ccm.yaim.block.BlockConsumer;
import ccm.yaim.block.BlockProvider;
import ccm.yaim.tiles.TileCable;
import ccm.yaim.tiles.TileConsumer;
import ccm.yaim.tiles.TileProvider;
import ccm.yaim.util.Data;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

@Mod(modid = Data.MODID, name = Data.MODID, dependencies = "required-after:ForgeMultipart")
public class YAIM
{
    @Mod.Instance(Data.MODID)
    public static YAIM instance;

    public static BlockCable    blockCable;
    public static BlockConsumer blockConsumer;
    public static BlockProvider blockProvider;

    @Mod.EventHandler
    public void fmlEvent(FMLPreInitializationEvent event)
    {
        blockCable = new BlockCable(901, new Material(MapColor.ironColor));
        blockConsumer = new BlockConsumer(902, new Material(MapColor.ironColor));
        blockProvider = new BlockProvider(903, new Material(MapColor.ironColor));

        GameRegistry.registerBlock(blockCable, "CABLE");
        GameRegistry.registerBlock(blockConsumer, "CONSUMER");
        GameRegistry.registerBlock(blockProvider, "PROVIDER");
    }

    @Mod.EventHandler
    public void fmlEvent(FMLInitializationEvent event)
    {
        GameRegistry.registerTileEntity(TileCable.class, Data.MODID + ".CABLE");
        GameRegistry.registerTileEntity(TileConsumer.class, Data.MODID + ".CONSUMER");
        GameRegistry.registerTileEntity(TileProvider.class, Data.MODID + ".PROVIDER");
    }
}
