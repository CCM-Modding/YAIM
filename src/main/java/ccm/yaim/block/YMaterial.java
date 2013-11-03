package ccm.yaim.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class YMaterial extends net.minecraft.block.material.Material
{
    public static final Material intance = new YMaterial();

    public YMaterial()
    {
        super(MapColor.ironColor);
        this.setNoPushMobility();
    }
}
