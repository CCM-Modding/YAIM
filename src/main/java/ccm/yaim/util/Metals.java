package ccm.yaim.util;

import static ccm.yaim.util.TheMetricSystem.Prefix.KILO;
import static ccm.yaim.util.TheMetricSystem.Prefix.NANO;
import static ccm.yaim.util.TheMetricSystem.Unit.*;

/**
 * This enum is used to get metals properties
 */
public enum Metals
{
    IRON(811, 97, 1538),
    GOLD(524, 22, 1064.18f),
    COPPER(753, 17, 1084.62f),
    TIN(59.1f, 110, 231.9f),
    SILVER(468, 16, 961.78f),
    ALUMINIUM(358, 26, 660.32f),
    STEEL(3017.8f, 49, 2401.69f),
    BRONZE(772.7f, 40, 1161.92f),
    LEAD(95.8f, 210, 327.46f),
    ELECTRUM(496, 19, 1012.98f),

    TITANUM(625, 400, 1668),
    ZINC(211, 59, 419.53f),
    BRASS(617.5f, 28, 918.35f),
    TUNGSTEN(13.7f, 50, 3422),
    PLATINUM(795, 110, 1768.3f),
    NICKEL(919, 70, 1455),
    OSMIUM(13.9f, 81, 3033),
    CADMIUM(120, 70, 321.07f),
    INDIUM(23.1f, 80, 156.6f),
    CHROME(974, 125, 1857),
    IRIDIUM(11.4f, 47, 2466),
    ADVANCED_ALLOY(2464.2f, 82, 4398.73f),
    TUNGSTEN_STEEL(3031.5f, 49, 5826.69f),

    COBALT(884, 60, 1495),
    ALUMINIUM_BRASS(566.55f, 47, 553.22f),
    ALUMITE(2756.1f, 41, 797.2f),
    MANYULLYN(6688, -1, -1),
    ARDITE(2460, -1, -1),

    REDSTONE(25.8f, -1, 800),
    GLOWSTONE(11.f, -1, 1000);

    public final SINumber meltEnergy;
    public final SINumber resistance;
    public final SINumber meltingTemp;

    private Metals(float kj, float nOhm, float t)
    {
        this.meltEnergy = SINumber.getMostAppropriate(ENERGY, kj * KILO.value);
        this.resistance = (nOhm == -1) ? null : SINumber.getMostAppropriate(RESISTANCE, nOhm * NANO.value);
        this.meltingTemp = SINumber.getMostAppropriate(TEMPERATURE, t);
    }
}
