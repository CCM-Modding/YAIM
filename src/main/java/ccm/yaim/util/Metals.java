package ccm.yaim.util;

import static ccm.yaim.util.TheMetricSystem.Prefix.*;
import static ccm.yaim.util.TheMetricSystem.Unit.*;

/**
 * This enum is used to get metals properties
 */
public enum Metals
{
    IRON(811, 97),
    GOLD(524, 22),
    COPPER(753, 17),
    TIN(59.1f, 110),
    SILVER(468, 16),
    ALUMINIUM(358, 26),
    STEEL(3017.8f, 49),
    BRONZE(772.7f, 40),
    LEAD(95.8f, 210),
    ELECTRUM(496, 19),

    TITANUM(625, 400),
    ZINC(211, 59),
    BRASS(617.5f, 28),
    TUNGSTEN(13.7f, 50),
    PLATINUM(795, 110),
    NICKEL(919, 70),
    OSMIUM(13.9f, 81),
    CADMIUM(120, 70),
    INDIUM(23.1f, 80),
    CHROME(974, 125),
    IRIDIUM(11.4f, 47),
    ADVANCED_ALLOY(2464.2f, 82),
    TUNGSTEN_STEEL(3031.5f, 49),

    COBALT(884, 60),
    ALUMINIUM_BRASS(566.55f, 47),
    ALUMITE(2756.1f, 41),
    MANYULLYN(6688),
    ARDITE(2460),

    REDSTONE(25.8f),
    GLOWSTONE(11.f),
    LAPIS(46.3f),
    OBSIDIAN(4956.3f);

    public final SINumber meltEnergy;
    public final SINumber resistance;

    private Metals(float kj)
    {
        this.meltEnergy = SINumber.getMostAppropriate(ENERGY, kj * KILO.value);
        this.resistance = null;
    }

    private Metals(float kj, float nOhm)
    {
        this.meltEnergy = SINumber.getMostAppropriate(ENERGY, kj * KILO.value);
        this.resistance = SINumber.getMostAppropriate(RESISTANCE, nOhm * NANO.value);
    }
}
