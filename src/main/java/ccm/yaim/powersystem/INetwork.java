package ccm.yaim.powersystem;

/**
 * This is the heart of the system.
 *
 * @author Dries007
 */
public interface INetwork
{
    /**
     * Make the network consume and provide power in the right places.
     */
    public void tick();

    /**
     * Call to recalculate the entire network.
     *
     * @return true if something changed
     */
    public boolean refresh();
}
