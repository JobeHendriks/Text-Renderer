import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;


public class TextFile {
    private File file;
    private FileInputStream fileInputStream;
    private ByteConverter byteConverter = new ByteConverter();
    byte[] bytes;
    ByteBuffer byteBuffer;
    int scalarType;
    short numTables,searchRange,entrySelector,rangeShift;

    public TextFile() throws IOException {
        file = new File(Const.TEXT_FILE);
        fileInputStream = new FileInputStream(file);
        bytes = new byte[(int)file.length()];
        fileInputStream.read(bytes);
        byteBuffer = ByteBuffer.wrap(bytes);


        scalarType = byteBuffer.getInt();
        numTables = byteBuffer.getShort();
        searchRange = byteBuffer.getShort();
        entrySelector = byteBuffer.getShort();
        rangeShift = byteBuffer.getShort();
        System.out.println(scalarType + ", " + numTables + ", " + searchRange + ", " + entrySelector + ", " + rangeShift);
        Table[] tableDirectory = new Table[numTables];

        for (int  i = 0; i < numTables; i++){
            String tag = new String( new byte[]{byteBuffer.get(), byteBuffer.get(), byteBuffer.get(), byteBuffer.get()});
            int checkSum = byteBuffer.getInt();
            int offset = byteBuffer.getInt();
            int length = byteBuffer.getInt();
            System.out.println("tag: " + tag + " Offset: " + offset);
            tableDirectory[i] = new Table(tag,checkSum,offset,length);
        }

        getGlyphs(tableDirectory);
    }

    public void getGlyphs(Table[] tables) {
        ByteBuffer glyfBuffer = null;
        for (Table table : tables) {
            if ("glyf".equals(table.tag)) {
                glyfBuffer = ByteBuffer.wrap(bytes,table.offset,table.length);
                break;
            }
        }
        short numberOfContours = glyfBuffer.getShort();
        short xMin = glyfBuffer.getShort();
        short yMin = glyfBuffer.getShort();
        short xMax = glyfBuffer.getShort();
        short yMax = glyfBuffer.getShort();
        short[] endPtsOfContours = new short[numberOfContours];
        for (int i = 0; i < numberOfContours; i++){
            endPtsOfContours[i] = glyfBuffer.getShort();
        }
        short numPoints =  endPtsOfContours[endPtsOfContours.length-1];
        short instructionsLength = glyfBuffer.getShort();
        short[] instructions = new short[instructionsLength];
        for (int i = 0; i < instructionsLength; i++){
            instructions[i] = glyfBuffer.get();
        }
    }
}

