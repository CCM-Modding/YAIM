package ccm.yaim.util;

import static ccm.yaim.util.TheMetricSystem.Prefix;
import static ccm.yaim.util.TheMetricSystem.Unit;

public class SINumber
{
    public Unit   unit;
    public Prefix prefix;
    public float  value;

    public SINumber(Unit unit, float value, Prefix prefix)
    {
        this.unit = unit;
        this.prefix = prefix;
        this.value = value;
    }

    public SINumber(Unit unit, float value)
    {
        this.unit = unit;
        this.value = value;
        this.prefix = Prefix.NONE;
    }

    public SINumber(float value)
    {
        this.unit = Unit.UNKNOWN;
        this.prefix = Prefix.NONE;
        this.value = value;
    }

    public SINumber(float value, Prefix prefix)
    {
        this.unit = Unit.UNKNOWN;
        this.prefix = prefix;
        this.value = value;
    }

    public static SINumber getMostAppropriate(float value)
    {
        return getMostAppropriate(Unit.UNKNOWN, value);
    }

    public static SINumber getMostAppropriate(Unit unit, float value)
    {
        Prefix prefix = Prefix.getMostAppropriate(value);
        return new SINumber(unit, value / prefix.value, prefix);
    }

    @Override
    public String toString()
    {
        return unit.symbol + "=" + Data.twoDForm.format(value) + " " + prefix.name + unit.name;
    }

    /**
     * @return The ACTUAL value. Including the prefix.
     */
    public float getValue()
    {
        return value * prefix.value;
    }
}
