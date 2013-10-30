package ccm.yaim.util;

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

        if (voltage != null && resistance != null)
            return SINumber.getMostAppropriate(TheMetricSystem.Unit.CURRENT, voltage.getValue() / resistance.getValue());
        else if (voltage != null && power != null)
            return SINumber.getMostAppropriate(TheMetricSystem.Unit.CURRENT, power.getValue() / voltage.getValue());
        else
            throw new IllegalArgumentException("I can't make up numbers...");
    }
}
