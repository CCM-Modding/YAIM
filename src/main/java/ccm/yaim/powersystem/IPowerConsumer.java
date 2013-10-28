package ccm.yaim.powersystem;

import ccm.yaim.util.SINumber;

/**
 * Use for TEs that use power.
 *
 * @author Dries007
 */
public interface IPowerConsumer extends INetworkPart
{
    /**
     * @return The resistance of this machine.
     */
    public SINumber getResistance();

    /**
     * @return The power factor of this machine. Always in [-1,1]
     */
    public SINumber getPowerFactor();

    /**
     * @param voltage The input voltage
     * @param amps    The current provided by the network
     */
    public void usePower(SINumber voltage, SINumber amps);
}
