package ccm.yaim.util;

/**
 * Thanks for the idea Calclavia!
 *
 * @author Dries007
 */
public class TheMetricSystem
{
    public static enum Unit
    {
        UNKNOWN("", "x"),
        CURRENT("Amp", "I"), VOLTAGE("Volt", "U"), RESISTANCE("Ohm", "Z"),
        ENERGY("Joule", "E"),
        REAL_POWER("Watt", "P"), APPARENT_POWER("VA", "S"), REACTIVE_POWER("VAr", "Q"),
        POWERFACTOR("", "PF"),
        TEMPERATURE("°C", "T"),
        LENGHT("m", "l"), AREA("m²", "A"), VOLUME("m³", "V");

        public final String name;
        public final String symbol;

        private Unit(String name, String symbol)
        {
            this.name = name;
            this.symbol = symbol;
        }
    }

    public static enum Prefix
    {
        YOTTA("Yotta", "Y", 24), ZETTA("Zetta", "Z", 21), EXA("Exa", "E", 18), PETA("Peta", "P", 15),
        TERA("Tera", "T", 12), GIGA("Giga", "G", 9), MEGA("Mega", "M", 6), KILO("Kilo", "k", 3),
        NONE("", "", 0),
        MILI("Mili", "m", -3), MICRO("Micro", "µ", -3), NANO("Nano", "n", -9), PICO("Pico", "p", -12);

        public final String name;
        public final String symbol;
        public final float  value;

        private Prefix(String name, String symbol, int power)
        {
            this.name = name;
            this.symbol = symbol;
            this.value = (float) Math.pow(10, power);
        }

        public static Prefix getMostAppropriate(float value)
        {
            for (Prefix prefix : Prefix.values())
            {
                if (prefix.value <= value) return prefix;
            }
            return PICO;
        }
    }
}
