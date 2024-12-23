package scopio.util;

import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.Arrays;

public class CompressionHandler {
    public static final int HEADER_SIZE = 4;

    public static byte[] decompress(byte[] compressedData) throws Exception {
        if (compressedData.length < HEADER_SIZE) {
            throw new IllegalArgumentException("Invalid compressed data");
        }

        Inflater inflater = new Inflater();
        inflater.setInput(compressedData, HEADER_SIZE, compressedData.length - HEADER_SIZE);
        byte[] buffer = new byte[compressedData.length * 4];
        int decompressedDataLength = inflater.inflate(buffer);
        inflater.end();
        byte[] decompressedData = new byte[decompressedDataLength];
        System.arraycopy(buffer, 0, decompressedData, 0, decompressedDataLength);
        return decompressedData;
    }

    public static byte[] compress(byte[] data) throws Exception {
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();
        byte[] buffer = new byte[data.length + HEADER_SIZE];
        int compressedDataLength = deflater.deflate(buffer, HEADER_SIZE, buffer.length - HEADER_SIZE);
        deflater.end();
        byte[] compressedData = Arrays.copyOf(buffer, compressedDataLength + HEADER_SIZE);
        System.arraycopy(intToByteArray(data.length), 0, compressedData, 0, HEADER_SIZE);
        return compressedData;
    }

    private static byte[] intToByteArray(int value) {
        return new byte[] {
            (byte) (value >> 24),
            (byte) (value >> 16),
            (byte) (value >> 8),
            (byte) value
        };
    }
}