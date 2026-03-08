import java.util.*;
import java.io.*;

/**
 * Singleton manager for user lookup, registration, and persistence.
 *
 * <p>Precondition: The manager must be initialized with {@link #load(String)} before use.
 * Postcondition: User collection remains available globally through {@link #getInstance()}.
 *
 * @see User
 * @see ReservationSystem
 */
public class UserManager {
    private final TreeSet<User> users = new TreeSet<>();
    private final File file;

    private static UserManager instance;



    /**
     * Creates the user manager and loads users from a backing file.
     *
     * <p>Precondition: {@code fileName != null}.
     * Postcondition: Backing file exists and parsed users are loaded into memory.
     *
     * @input fileName base filename without extension
     * @see #load(String)
     */
    private UserManager(String fileName) throws IOException {
        file = new File(fileName + ".txt");
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
            System.out.println(data[2]);
            users.add(new User(data[0], data[1], Integer.parseInt(data[2]) == 1));
        }
        br.close();
        fr.close();
    }

    /**
     * Returns the current singleton manager instance.
     *
     * <p>Precondition: {@link #load(String)} has already been called.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return active singleton instance
     * @see #load(String)
     */
    public static UserManager getInstance() {
        return instance;
    }

    /**
     * Finds a user by username and password.
     *
     * <p>Precondition: {@code username != null} and {@code password != null}.
     * Postcondition: No state is modified.
     *
     * @input username candidate username
     * @input password candidate password
     * @return matching user or {@code null} when credentials do not match
     * @see User#equals(String, String)
     */
    public User getUser(String username, String password) {
        for  (User user : users) {
            if(user.equals(username, password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Finds a user by username only.
     *
     * <p>Precondition: {@code username != null}.
     * Postcondition: No state is modified.
     *
     * @input username target username
     * @return matching user or {@code null} when not found
     * @see User#equals(String)
     */
    public User getUser(String username) {
        for  (User user : users) {
            if(user.equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Returns the user collection.
     *
     * <p>Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return sorted user set
     * @see User#compareTo(User)
     */
    public TreeSet<User> getUsers() { return users; }

    /**
     * Writes all users to the backing file.
     *
     * <p>Precondition: Backing file is available and writable.
     * Postcondition: File contents are replaced with current user set.
     *
     * @input none
     * @return none
     * @see User#toString()
     */
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

    /**
     * Initializes and returns the singleton manager.
     *
     * <p>Precondition: {@code fileName != null}.
     * Postcondition: Singleton instance exists and remains reusable for future calls.
     *
     * @input fileName base filename without extension
     * @return singleton manager instance
     * @see #getInstance()
     */
    public static UserManager load(String fileName) throws IOException {
        if (instance == null) {
            instance = new UserManager(fileName);
        }
        return instance;
    }

    /**
     * Adds a new non-admin user to the collection.
     *
     * <p>Precondition: {@code username != null} and {@code password != null}.
     * Postcondition: User set contains a user with the provided credentials.
     *
     * @input username username for new account
     * @input password password for new account
     * @return none
     * @see User#User(String, String, boolean)
     */
    public void addUser(String username, String password) {
        users.add(new User(username, password, false));
    }
}
