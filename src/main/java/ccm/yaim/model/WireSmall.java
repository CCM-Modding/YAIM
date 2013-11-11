package ccm.yaim.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeDirection;

public class WireSmall extends ModelBase implements ISidedConnectorModel
{
    public static final WireSmall INSTANCE = new WireSmall();
    ResourceLocation texture = new ResourceLocation("yaim:textures/models/wire_small.png");
    ModelRenderer Center;
    ModelRenderer Top;
    ModelRenderer Bottom;
    ModelRenderer North;
    ModelRenderer South;
    ModelRenderer East;
    ModelRenderer West;

    private WireSmall()
    {
        textureWidth = 64;
        textureHeight = 64;

        Center = new ModelRenderer(this, 0, 0);
        Center.addBox(0F, 0F, 0F, 4, 4, 4);
        Center.setRotationPoint(-2F, 14F, -2F);
        Center.setTextureSize(64, 64);
        Center.mirror = true;
        Top = new ModelRenderer(this, 0, 13);
        Top.addBox(0F, 0F, 0F, 4, 6, 4);
        Top.setRotationPoint(-2F, 8F, -2F);
        Top.setTextureSize(64, 64);
        Top.mirror = true;
        Bottom = new ModelRenderer(this, 0, 13);
        Bottom.addBox(0F, 0F, 0F, 4, 6, 4);
        Bottom.setRotationPoint(-2F, 18F, -2F);
        Bottom.setTextureSize(64, 64);
        Bottom.mirror = true;
        North = new ModelRenderer(this, 22, 0);
        North.addBox(0F, 0F, 0F, 4, 4, 6);
        North.setRotationPoint(-2F, 14F, 2F);
        North.setTextureSize(64, 64);
        North.mirror = true;
        South = new ModelRenderer(this, 22, 0);
        South.addBox(0F, 0F, 0F, 4, 4, 6);
        South.setRotationPoint(-2F, 14F, -8F);
        South.setTextureSize(64, 64);
        South.mirror = true;
        East = new ModelRenderer(this, 44, 0);
        East.addBox(0F, 0F, 0F, 6, 4, 4);
        East.setRotationPoint(2F, 14F, -2F);
        East.setTextureSize(64, 64);
        East.mirror = true;
        West = new ModelRenderer(this, 44, 0);
        West.addBox(0F, 0F, 0F, 6, 4, 4);
        West.setRotationPoint(-8F, 14F, -2F);
        West.setTextureSize(64, 64);
        West.mirror = true;
    }

    @Override
    public ResourceLocation getTexture()
    {
        return texture;
    }

    @Override
    public void renderCenter()
    {
        Center.render(0.0625F);
    }

    @Override
    public void renderSide(ForgeDirection side)
    {
        switch (side)
        {
            case UP:
                Top.render(0.0625F);
                break;
            case DOWN:
                Bottom.render(0.0625F);
                break;
            case NORTH:
                North.render(0.0625F);
                break;
            case EAST:
                East.render(0.0625F);
                break;
            case SOUTH:
                South.render(0.0625F);
                break;
            case WEST:
                West.render(0.0625F);
                break;
        }
    }

    @Override
    public void renderAll()
    {
        renderCenter();
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) renderSide(side);
    }
}
