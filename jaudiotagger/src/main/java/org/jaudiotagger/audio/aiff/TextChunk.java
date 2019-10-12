package org.jaudiotagger.audio.aiff;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/**
 * This class provides common functionality for NameChunk, AuthorChunk,
 * and CopyrightChunk
 */
public abstract class TextChunk extends Chunk {

    protected String chunkText;
    private AiffAudioHeader aiffHeader;

    /**
     * Constructor.
     *
     * @param hdr The header for this chunk
     * @param raf The file from which the AIFF data are being read
     */
    public TextChunk(
            ChunkHeader hdr,
            RandomAccessFile raf) {
        super(raf, hdr);
    }

    /**
     * Read the chunk. The subclasses need to take the value of
     * chunkText and use it appropriately.
     */
    @Override
    public boolean readChunk() throws IOException {

        byte[] buf = new byte[(int) bytesLeft];
        raf.read(buf);
        chunkText = new String(buf, StandardCharsets.ISO_8859_1);
        return true;
    }

}
