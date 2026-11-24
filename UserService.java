public class UserService {
    public String getUserEmail(User user) {
        // Deliberate bug: no null check before calling getEmail()
        return user.getEmail().toLowerCase();
    }
}