public class Test {
    public static void main(String[] args) {
        System.out.println(isHexadecimal("0x00GG00"));

    }
    public static boolean isHexadecimal(String str) {
        if(str == null || str.isEmpty()) {
            return false; // if string is null or empty, it cannot be a hexadecimal number
        }
        String hexPattern = "^0[xX][0-9A-F]+$"; // regular expression pattern to match a hexadecimal number
        return str.matches(hexPattern);
    }
}
