package ccm.yaim.parts;

import ccm.yaim.util.SINumber;

/**
 * Use for wires.
 *
 * @author Dries007
 */
public interface IConductor extends INetworkPart
{
    /**
     * @return The amount of resistance in Ohms per block.
     */
    public SINumber getResistancePerMeter();

    /**
     * @return The maximum of amps you can pull through this wire.
     */
    public SINumber getMaxCurrent();

    /**
     * Gets called when the wire should melt.
     */
    public void melt();
}
