package alistair.utility;

public final class Validate {
    public static boolean id(String id){
        if (id.length() == 3)
            return id.matches("[1-9]\\d{2}");
        else if (id.length() == 4)
            return id.matches("[1-9]\\d{3}");
        else if (id.length() == 5)
            return id.matches("[1-9]\\d{4}");
        else
            return false;
    }
    public static boolean rid(String id){
        return id.matches("\\d+");
    }
    public static boolean word(String word){
        return word.matches("\\w+");
    }
    public static boolean email(String email){
        return email.contains("@") && email.endsWith(".com") || email.endsWith(".ac.za") ||
                email.endsWith(".net") || email.endsWith(".co.za");
    }

    public static boolean date(String date){
        return date.matches("((?:19|20)[0-9][0-9])-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])");
    }
    public static boolean time(String time){
        return time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]");
    }
    public static boolean phone(String phone){
        return  phone.matches("[0]\\d{9}");
    }
    public static boolean firstName(String firstname){
        return firstname.matches("[A-Z][a-zA-Z]*");
    }
    public static boolean lastName(String lastname){
        return lastname.matches("[a-zA-z]+(['-][a-zA-Z]+)*");
    }
    public static boolean streetAddress(String address){
        return address.matches("\\d+\\s+([a-zA-Z]+\\s[a-zA-Z]+)");
    }
    public static boolean city(String city){
        return city.matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
    }
    public static boolean suburb(String state){
        return state.matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z])");
    }
    public static boolean postCode(String zip){
        return zip.matches("\\d{4}|\\d{5}");
    }
}
