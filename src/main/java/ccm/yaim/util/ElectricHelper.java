package ccm.yaim.util;

import ccm.yaim.parts.INetworkPart;
import codechicken.lib.vec.BlockCoord;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ElectricHelper
{
    public static SINumber getAmps(SINumber... numbers)
    {
        SINumber voltage = null;
        SINumber resistance = null;
        SINumber power = null;
        for (SINumber number : numbers)
        {
            switch (number.unit)
            {
                case CURRENT:
                    return number;
                case VOLTAGE:
                    voltage = number;
                    break;
                case RESISTANCE:
                    resistance = number;
                    break;
                case POWER:
                    power = number;
                    break;
                default:
                    throw new IllegalArgumentException("What do I need this for?");
            }
        }

        if (voltage != null && resistance != null) return SINumber.getMostAppropriate(TheMetricSystem.Unit.CURRENT, voltage.getValue() / resistance.getValue());
        else if (voltage != null && power != null) return SINumber.getMostAppropriate(TheMetricSystem.Unit.CURRENT, power.getValue() / voltage.getValue());
        else throw new IllegalArgumentException("I can't make up numbers...");
    }

    public static INetworkPart[] getAdjacentParts(World world, BlockCoord origin)
    {
        TileEntity originTe = world.getBlockTileEntity(origin.x, origin.y, origin.z);
        INetworkPart[] adjacentParts = new INetworkPart[6];
        for (int i = 0; i < 6; i++)
        {
            BlockCoord coord = origin.copy().offset(i);
            TileEntity te = world.getBlockTileEntity(coord.x, coord.y, coord.z);

            if (containsCable(te)) adjacentParts[i] = getCable(te);
        }
        return adjacentParts;
    }

    public static boolean containsCable(TileEntity te)
    {
        if (te instanceof INetworkPart)
        {
            return true;
        }
        else if (te instanceof TileMultipart)
        {
            TileMultipart tem = (TileMultipart) te;

            for (TMultiPart t : tem.jPartList())
            {
                if (t instanceof INetworkPart) return true;
            }
        }
        return false;
    }

    public static INetworkPart getCable(TileEntity te)
    {
        if (te instanceof INetworkPart)
        {
            return (INetworkPart) te;
        }
        else if (te instanceof TileMultipart)
        {
            TileMultipart tem = (TileMultipart) te;

            for (TMultiPart t : tem.jPartList())
            {
                if (t instanceof INetworkPart) return (INetworkPart) t;
            }
        }
        return null;
    }
}
