import java.util.ArrayList;

public interface IReadData {
    public boolean openDataFile(String filename);
    public boolean readDataFile();
    public ArrayList<String> getRawDataList();
}
