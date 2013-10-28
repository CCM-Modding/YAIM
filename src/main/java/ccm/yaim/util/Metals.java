package ccm.yaim.util;

public enum Metals
{
    IRON(811),
    GOLD(524),
    COPPER(753),
    TIN(59.1f),
    SILVER(468),
    ALUMINIUM(358),
    STEEL(3017.8f),
    BRONZE(772.7f),
    LEAD(95.8f),
    ELECTRUM(496),

    TITANUM(625),
    ZINC(211),
    BRASS(617.5f),
    TUNGSTEN(13.7f),
    PLATINUM(795),
    NICKEL(919),
    OSMIUM(13.9f),
    CADMIUM(120),
    INDIUM(23.1f),
    CHROME(974),
    IRIDIUM(11.4f),
    ADVANCED_ALLOY(2464.2f),
    TUNGSTEN_STEEL(3031.5f),

    COBALT(884),
    ALUMINIUM_BRASS(566.55f),
    ALUMITE(2756.1f),
    MANYULLYN(6688f),
    ARDITE(2460f),

    REDSTONE(25.8f),
    GLOWSTONE(11.f),
    LAPIS(46.3f),
    OBSIDIAN(4956.3f);


    public final SINumber meltEnergy;

    private Metals(float kj)
    {
        meltEnergy = SINumber.getMostAppropriate(TheMetricSystem.Unit.ENERGY, kj * TheMetricSystem.Prefix.KILO.value);
    }
}
