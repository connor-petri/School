/**
 * Represents an authenticated system user.
 *
 * <p>Precondition: Username and password are expected to be non-null strings.
 * Postcondition: User identity and admin role remain immutable after construction.
 *
 * @see UserManager
 */
public class User implements Comparable<User> {
    private final String username;
    private final String password;
    private final boolean admin;

    /**
     * Creates a user with credentials and role metadata.
     *
     * <p>Precondition: {@code username != null} and {@code password != null}.
     * Postcondition: Immutable user fields are initialized.
     *
     * @input username account username
     * @input password account password
     * @input admin indicates whether the account has admin privileges
     * @see User#isAdmin()
     */
    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    /**
     * Returns the username.
     *
     * <p>Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return username value
     * @see #toString()
     */
    public String getUsername() { return username; }

    /**
     * Returns whether this user has admin privileges.
     *
     * <p>Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return {@code true} if admin, otherwise {@code false}
     * @see UserManager#save()
     */
    public boolean isAdmin() { return admin; }

    /**
     * Compares provided credentials to this user.
     *
     * <p>Precondition: {@code username != null} and {@code password != null}.
     * Postcondition: No state is modified.
     *
     * @input username candidate username
     * @input password candidate password
     * @return {@code true} if both username and password match; otherwise {@code false}
     * @see UserManager#getUser(String, String)
     */
    public boolean equals(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    /**
     * Compares provided username to this user.
     *
     * <p>Precondition: {@code username != null}.
     * Postcondition: No state is modified.
     *
     * @input username candidate username
     * @return {@code true} if username matches; otherwise {@code false}
     * @see UserManager#getUser(String)
     */
    public boolean equals(String username) {
        return this.username.equals(username);
    }

    /**
     * Orders users lexicographically by username.
     *
     * <p>Precondition: {@code other != null}.
     * Postcondition: No state is modified.
     *
     * @input other user to compare against
     * @return negative, zero, or positive according to username ordering
     * @see Comparable#compareTo(Object)
     */
    public int compareTo(User other) {
        return username.compareTo(other.username);
    }

    /**
     * Serializes user data to CSV-compatible text.
     *
     * <p>Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return CSV string in format {@code username,password,isAdminFlag}
     * @see UserManager#save()
     */
    public String toString() {
        return username + "," +  password + "," + (admin ? 1 : 0);
    }
}
