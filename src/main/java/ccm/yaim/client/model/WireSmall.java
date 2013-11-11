package ccm.yaim.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class WireSmall extends ModelBase
{
    //fields
    ModelRenderer Center;
    ModelRenderer Top;
    ModelRenderer Bottom;
    ModelRenderer North;
    ModelRenderer South;
    ModelRenderer East;
    ModelRenderer West;

    public WireSmall()
    {
        textureWidth = 64;
        textureHeight = 64;

        Center = new ModelRenderer(this, 0, 0);
        Center.addBox(0F, 0F, 0F, 4, 4, 4);
        Center.setRotationPoint(-2F, 14F, -2F);
        Center.setTextureSize(64, 64);
        Center.mirror = true;
        setRotation(Center, 0F, 0F, 0F);
        Top = new ModelRenderer(this, 0, 13);
        Top.addBox(0F, 0F, 0F, 4, 6, 4);
        Top.setRotationPoint(-2F, 8F, -2F);
        Top.setTextureSize(64, 64);
        Top.mirror = true;
        setRotation(Top, 0F, 0F, 0F);
        Bottom = new ModelRenderer(this, 0, 13);
        Bottom.addBox(0F, 0F, 0F, 4, 6, 4);
        Bottom.setRotationPoint(-2F, 18F, -2F);
        Bottom.setTextureSize(64, 64);
        Bottom.mirror = true;
        setRotation(Bottom, 0F, 0F, 0F);
        North = new ModelRenderer(this, 22, 0);
        North.addBox(0F, 0F, 0F, 4, 4, 6);
        North.setRotationPoint(-2F, 14F, 2F);
        North.setTextureSize(64, 64);
        North.mirror = true;
        setRotation(North, 0F, 0F, 0F);
        South = new ModelRenderer(this, 22, 0);
        South.addBox(0F, 0F, 0F, 4, 4, 6);
        South.setRotationPoint(-2F, 14F, -8F);
        South.setTextureSize(64, 64);
        South.mirror = true;
        setRotation(South, 0F, 0F, 0F);
        East = new ModelRenderer(this, 44, 0);
        East.addBox(0F, 0F, 0F, 6, 4, 4);
        East.setRotationPoint(2F, 14F, -2F);
        East.setTextureSize(64, 64);
        East.mirror = true;
        setRotation(East, 0F, 0F, 0F);
        West = new ModelRenderer(this, 44, 0);
        West.addBox(0F, 0F, 0F, 6, 4, 4);
        West.setRotationPoint(-8F, 14F, -2F);
        West.setTextureSize(64, 64);
        West.mirror = true;
        setRotation(West, 0F, 0F, 0F);
    }

    public void renderCenter()
    {
        Center.render(0.0625F);
    }

    public void renderBottom()
    {
        Bottom.render(0.0625F);
    }

    public void renderTop()
    {
        Top.render(0.0625F);
    }

    public void renderNorth()
    {
        North.render(0.0625F);
    }

    public void renderEast()
    {
        East.render(0.0625F);
    }

    public void renderSouth()
    {
        South.render(0.0625F);
    }

    public void renderWest()
    {
        West.render(0.0625F);
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.render(entity, f, f1, f2, f3, f4, f5);
        Center.render(f5);
        Top.render(f5);
        Bottom.render(f5);
        North.render(f5);
        South.render(f5);
        East.render(f5);
        West.render(f5);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
