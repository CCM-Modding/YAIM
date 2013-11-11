package ccm.yaim.parts;

import ccm.yaim.util.SINumber;

/**
 * Use for TEs that provide power.
 *
 * @author Dries007
 */
public interface IPowerProvider extends INetworkPart, Comparable<IPowerProvider>
{
    /**
     * @return The voltage this emitter outputs
     */
    public SINumber getVoltage();

    /**
     * @return The max power the TE can provide at once.
     */
    public SINumber getMaxPower();

    /**
     * @param amps The amount of amps consumed
     */
    public void providePower(SINumber amps);

    /**
     * @return highest number gets consumed first.
     */
    public byte getProviderPriority();
}
