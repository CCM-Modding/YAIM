package ccm.yaim.network;

import ccm.yaim.parts.INetworkPart;
import net.minecraft.world.World;

import java.util.Set;

/**
 * This is the heart of the system.
 *
 * @author Dries007
 */
public interface INetwork
{
    public Set<INetworkPart> getParts();

    //public Set<IConductor> getConductors();

    //public Set<IPowerConsumer> getPowerConsumers();

    //public Set<IPowerProvider> getPowerProviders();

    /**
     * Call to recalculate the entire network.
     */
    public void refresh(INetworkPart... ignoreTiles);

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

    boolean isEmpty();

    void clear();

    void split(INetworkPart part);
}
