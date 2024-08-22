public class ByteConverter {

    public short twoByteToShort(byte[] b){
        if (b.length != 2){
            throw new RuntimeException("incorrect number of bytes");
        }
        return (short) (b[0] << 8 | b[1] & 0xFF);
    }

    public String tag(byte[] b){
        if (b.length != 4){
            throw new RuntimeException("incorrect number of bytes");
        }
        byte[] bytes = new byte[]{b[0],b[1],b[2],b[3]};
        return new String(bytes);
    }

    public long fourByteToInt(byte[] b){
        if (b.length != 4){
            throw new RuntimeException("incorrect number of bytes");
        }
        int test = (b[0] << 24) + ( b[1] << 16) + (b[2] << 8) + (b[3]);
        long output = (0xFFFFFFFFL & test);
        return  output;
    }
}
