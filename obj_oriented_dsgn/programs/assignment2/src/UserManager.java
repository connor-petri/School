import java.util.*;
import java.io.*;

public class UserManager {
    private final TreeSet<User> users = new TreeSet<>();
    private final File file;

    private static UserManager instance;

    private UserManager(String fileName) throws IOException {
        file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            System.out.println("User file " + fileName + " created.");
            return;
        }
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        List<String> lines = br.readAllLines();
        String[] data;
        for (String line : lines) {
            if (line.length() < 3) { continue; }
            data = line.split(",");
            users.add(new User(data[0], data[1], Integer.parseInt(data[2]) == 0));
        }
        br.close();
        fr.close();
    }

    public void save() throws IOException {
        if (file == null) {
            return;
        }

        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        for (User user : users) {
            bw.write(user.toString());
            bw.newLine();
        }
        bw.close();
        fw.close();
    }

    public UserManager load(String fileName) throws IOException {
        if (instance == null) {
            instance = new UserManager(fileName);
        }
        return instance;
    }

    public static UserManager getInstance() { return instance; }

    public User getUser(String username) {
        for  (User user : users) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public TreeSet<User> getUsers() { return users; }
}
