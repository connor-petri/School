/**
 * Represents an authenticated system user.
 *
 * Precondition: Username and password are expected to be non-null strings.
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
     * Precondition: username != null and password != null.
     * Postcondition: Immutable user fields are initialized.
     *
     * @input username account username
     * @input password account password
     * @input admin indicates whether the account has admin privileges
     * @see User
     */
    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    /**
     * Returns the username.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return username value
   * @see User
     */
    public String getUsername() { return username; }

    /**
     * Returns whether this user has admin privileges.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return true if admin, otherwise false
     * @see UserManager
     */
    public boolean isAdmin() { return admin; }

    /**
     * Compares provided credentials to this user.
     *
     * Precondition: username != null and password != null.
     * Postcondition: No state is modified.
     *
     * @input username candidate username
     * @input password candidate password
     * @return true if both username and password match; otherwise false
     * @see UserManager
     */
    public boolean equals(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    /**
     * Compares provided username to this user.
     *
     * Precondition: username != null.
     * Postcondition: No state is modified.
     *
     * @input username candidate username
     * @return true if username matches; otherwise false
     * @see UserManager
     */
    public boolean equals(String username) {
        return this.username.equals(username);
    }

    /**
     * Orders users lexicographically by username.
     *
     * Precondition: other != null.
     * Postcondition: No state is modified.
     *
     * @input other user to compare against
     * @return negative, zero, or positive according to username ordering
     * @see Comparable
     */
    public int compareTo(User other) {
        return username.compareTo(other.username);
    }

    /**
     * Serializes user data to CSV-compatible text.
     *
     * Precondition: None.
     * Postcondition: No state is modified.
     *
     * @input none
     * @return CSV string in format username,password,isAdminFlag
     * @see UserManager
     */
    public String toString() {
        return username + "," +  password + "," + (admin ? 1 : 0);
    }
}
