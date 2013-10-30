package ccm.yaim.util;

public class PowerPacket
{
    public SINumber voltage;
    public SINumber amps;

    public PowerPacket(SINumber... numbers)
    {
        set(numbers);
    }

    public void set(SINumber... numbers)
    {
        for (SINumber number : numbers)
        {
            switch (number.unit)
            {
                case VOLTAGE:
                    voltage = number;
                    break;
                case CURRENT:
                    amps = number;
                default:
                    throw new IllegalArgumentException("Only Current and Voltage are accepted arguments!");
            }
        }
    }
}
