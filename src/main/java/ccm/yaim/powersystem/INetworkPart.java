package ccm.yaim.powersystem;

/**
 * Extended by all parts of a network.
 *
 * @author Dries007
 */
public interface INetworkPart
{
    public INetwork getNetwork();

    public void setNetwork(INetwork network);
}
