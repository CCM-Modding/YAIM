package ccm.yaim.parts;

import ccm.yaim.util.SINumber;

/**
 * Use for TEs that use power.
 *
 * @author Dries007
 */
public interface IPowerConsumer extends INetworkPart, Comparable<IPowerConsumer>
{
    public void usePower(SINumber power);

    /**
     * @return the amount of power you would like
     */
    public SINumber getPowerRequirement();

    public byte getConsumerPriority();
}
