package ccm.yaim;

import net.minecraftforge.common.Configuration;

import java.io.File;

public class Yaimconfig
{
    private Configuration configuration;

    public int block_cable;
    public int block_provider;
    public int block_consumer;

    public void doConfig(File suggestedConfigurationFile)
    {
        configuration = new Configuration(suggestedConfigurationFile);

        configuration.addCustomCategoryComment(Configuration.CATEGORY_BLOCK, "BlockIDs");
        block_cable = configuration.getBlock("cable", 901).getInt();
        block_provider = configuration.getBlock("provider", 902).getInt();
        block_consumer = configuration.getBlock("consumer", 903).getInt();

        configuration.save();
    }
}
