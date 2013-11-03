package ccm.yaim;

import ccm.yaim.block.BlockCable;
import ccm.yaim.block.BlockConsumer;
import ccm.yaim.block.BlockProvider;
import ccm.yaim.block.YMaterial;
import ccm.yaim.cmd.CommandYAIMDebug;
import ccm.yaim.multipart.Content;
import ccm.yaim.network.NetworkTicker;
import ccm.yaim.tiles.TileCable;
import ccm.yaim.tiles.TileConsumer;
import ccm.yaim.tiles.TileProvider;
import ccm.yaim.util.Data;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

@Mod(modid = Data.MODID, name = Data.MODID, dependencies = "required-after:ForgeMultipart")
public class YAIM
{
    @Mod.Instance(Data.MODID)
    public static YAIM instance;

    public BlockCable    blockCable;
    public BlockConsumer blockConsumer;
    public BlockProvider blockProvider;

    public Yaimconfig yaimconfig = new Yaimconfig();

    @Mod.EventHandler
    public void fmlEvent(FMLPreInitializationEvent event)
    {
        yaimconfig.doConfig(event.getSuggestedConfigurationFile());

        blockCable = new BlockCable(yaimconfig.block_cable, YMaterial.intance);
        blockConsumer = new BlockConsumer(yaimconfig.block_consumer, YMaterial.intance);
        blockProvider = new BlockProvider(yaimconfig.block_provider, YMaterial.intance);

        GameRegistry.registerBlock(blockCable, "CABLE");
        GameRegistry.registerBlock(blockConsumer, "CONSUMER");
        GameRegistry.registerBlock(blockProvider, "PROVIDER");

        LanguageRegistry.addName(blockCable, "Cable");
        LanguageRegistry.addName(blockConsumer, "Consumer");
        LanguageRegistry.addName(blockProvider, "Provider");

        new Content().init();
    }

    @Mod.EventHandler
    public void fmlEvent(FMLInitializationEvent event)
    {
        GameRegistry.registerTileEntity(TileCable.class, Data.MODID + ".CABLE");
        GameRegistry.registerTileEntity(TileConsumer.class, Data.MODID + ".CONSUMER");
        GameRegistry.registerTileEntity(TileProvider.class, Data.MODID + ".PROVIDER");

        TickRegistry.registerTickHandler(NetworkTicker.INSTANCE, Side.SERVER);
    }

    @Mod.EventHandler
    public void fmlEvent(FMLServerStartingEvent event)
    {
        NetworkTicker.INSTANCE.clear();
        event.registerServerCommand(new CommandYAIMDebug());
    }
}
