package ccm.yaim.network;

import ccm.yaim.parts.IConductor;
import ccm.yaim.parts.INetworkPart;
import ccm.yaim.parts.IPowerConsumer;
import ccm.yaim.parts.IPowerProvider;
import net.minecraft.world.World;

import java.util.Set;

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
     * @return all of the conductors in this network
     */
    public Set<IConductor> getConductors();

    /**
     * @return all of the power consumers in this network
     */
    public Set<IPowerConsumer> getPowerConsumers();

    /**
     * @return all of the power providers in this network
     */
    public Set<IPowerProvider> getPowerProviders();

    /**
     * Call to recalculate the entire network.
     *
     * @return true if something changed
     */
    public void refresh(INetworkPart part);

    /**
     * @return the world this network is part of
     */
    public World getWorld();
}
