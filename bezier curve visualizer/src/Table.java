public class Table {

    public String tag;
    public int checkSum,offset,length;

    public Table(String tag,int checkSum,int offset,int length){
        this.tag = tag;
        this.checkSum = checkSum;
        this.offset = offset;
        this.length = length;
    }
}
