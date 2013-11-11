package ccm.yaim.model;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;

public interface ISidedConnectorModel
{
    public ResourceLocation getTexture();
    public void renderCenter();
    public void renderSide(ForgeDirection side);
    public void renderAll();
}
