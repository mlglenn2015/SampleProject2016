package prv.mark.project.batchclient.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

/**
 * Example Spring Batch ChunkListener for ....
 *
 * @author mlglenn
 */
@Component
public class BatchClientChunkListener implements ChunkListener {

    private static final Logger logger = LoggerFactory.getLogger(BatchClientChunkListener.class);

    /**
     * The event that fires prior to the execution.
     * @param var1 @{link ChunkContext}
     */
    @Override
    public void beforeChunk(final ChunkContext var1) {
        logger.debug("*** BatchClientChunkListener.beforeChunk() ***");
    }

    /**
     * The event that fires after the execution.
     * @param var1 @{link ChunkContext}
     */
    @Override
    public void afterChunk(final ChunkContext var1) {
        logger.debug("*** BatchClientChunkListener.afterChunk() ***");
    }

    /**
     * The event that fires in case of error while executing.
     * @param var1 @{link ChunkContext}
     */
    @Override
    public void afterChunkError(final ChunkContext var1) {
        logger.debug("*** BatchClientChunkListener.afterChunkError() ***");
    }
}
