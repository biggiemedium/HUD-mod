package dev.px.hud.Util.Event.Bus.Events;

/**
 * Implementation of {@link ICancellable}
 *
 * @author Brady
 * @since 2/10/2017
 */
public class Cancellable implements ICancellable {

    /**
     * Cancelled state
     */
    private boolean cancelled;

    @Override
    public void cancel() {
        this.cancelled = true;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }
}