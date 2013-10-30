package ccm.yaim.network;

import ccm.yaim.parts.IConductor;
import ccm.yaim.parts.INetworkPart;
import ccm.yaim.parts.IPowerConsumer;
import ccm.yaim.parts.IPowerProvider;
import ccm.yaim.util.SINumber;
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
     * @return all the parts that make up this network
     */
    public Set<INetworkPart> getParts();

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
     */
    public void refresh();

    /**
     * @return the world this network is part of
     */
    public World getWorld();

    /**
     * Merge 2 networks
     *
     * @param network
     */
    public void merge(INetwork network);

    void remove(INetworkPart part);

    void add(INetworkPart part);

    void tick();
}
