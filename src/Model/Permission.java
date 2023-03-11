package Model;

/**
 * Class to hold a boolean value showing whether user is admin or not.
 *
 * @author Scott VanBrunt
 */
public class Permission {
    private static boolean verified = false;

    /**
     *
     * @return boolean true if logged in an admin, false otherwise.
     */
    public static boolean isVerified() {
        return verified;
    }

    /**
     *
     * @param verified boolean true if admin, false otherwise.
     */
    public static void setVerified(boolean verified) {
        Permission.verified = verified;
    }
}
