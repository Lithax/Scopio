package scopio.util;

public class ByteString {
    private byte[] bytes;

    public ByteString(byte[] bytes) {
        this.bytes = bytes.clone();
    }

    public byte[] subBytes(int begin, int end) {
        byte[] e = new byte[end-begin];
        for(; begin < end; begin++) {
            e[begin] = bytes[begin];
        }
        return e;
    }

    public int indexOf(char ch) {
        for(int i = 0; i < bytes.length; i++) {
            if(bytes[i] == (int) ch) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(char ch) {
        int l = -1;
        for(int i = 0; i < bytes.length; i++) {
            if(bytes[i] == (int) ch) {
                l = i;
            }
        }
        return l;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public int getLength() {
        return bytes.length;
    }
}