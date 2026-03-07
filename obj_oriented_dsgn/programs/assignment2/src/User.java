public class User implements Comparable<User> {
    private final String username;
    private final String password;
    private final boolean admin;

    public User(String username, String password, boolean admin) {
        this.username = username;
        this.password = password;
        this.admin = admin;
    }

    public String getUsername() { return username; }
    public boolean isAdmin() { return admin; }

    public boolean equals(User other) {
        return other.username.equals(username) && other.password.equals(password);
    }

    public int compareTo(User other) {
        return username.compareTo(other.username);
    }

    public String toString() {
        return username + "," +  password + "," + (admin ? 1 : 0);
    }
}
