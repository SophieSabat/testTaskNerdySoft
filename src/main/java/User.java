public class User {
    private String name;
    private int age;
    public static User createUser(String name, int age) {
        User user = new User();

        user.name = name;
        user.age = age;

        return user;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
