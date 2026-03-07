public class User {
    private int id;
    private String name;
    private boolean admin = false;

    public User(String name, boolean admin) {
        this.name = name;
        this.admin = admin;
    }

    public String getName() { return name; }
    public boolean isAdmin() { return admin; }
}
