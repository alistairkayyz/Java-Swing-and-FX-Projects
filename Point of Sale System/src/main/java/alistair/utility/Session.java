package alistair.utility;

public final class Session {
    private static int id;

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Session.id = id;
    }
}
