package ccm.yaim;

import ccm.yaim.util.Data;
import ccm.yaim.util.SINumber;
import ccm.yaim.util.TheMetricSystem;
import cpw.mods.fml.common.Mod;

@Mod(modid = Data.MODID, name = Data.MODID)
public class YAIM
{
    public YAIM()
    {
        for (int i = -15; i < 15; i++)
        {
            float f = (float) (Math.pow(10, i) + Math.random() * 1000 * Math.pow(10, i));
            System.out.println(f + ": " + SINumber.getMostAppropriate(TheMetricSystem.Unit.VOLTAGE, f).toString());
        }
    }
}
