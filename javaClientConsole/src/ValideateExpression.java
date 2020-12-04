/**
 * @author abdou
 */
public class ValideateExpression {

    /**
     *
     * @param val
     * @return
     */
    public static boolean isInt(String val) {
        //String regExp = "^.+@.+\\..+$";
        //String regExp = "^[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[a-z][a-z]+$";
        //String regExp = "^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$";
        String regExp = "[0-3]";
        return val.matches( regExp );
    }

    /**
     *
     * @param val
     * @return
     */
    public static boolean isValideCodeProd(String val) {
        //String regExp = "^[A-Za-z0-9._-]+@[A-Za-z0-9._-]+\\.[a-z][a-z]+$";
        //String regExp = "^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$";
        String regExp = "^[A-Z][a-z]{3}[A-Z][a-z]{3}";
        return val.matches( regExp );
    }

    public static void main(String[] args) {
        System.out.println(isInt("8"));
        System.out.println(isValideCodeProd("abdo"));
        System.out.println(isValideCodeProd("Abdo"));
        System.out.println(isValideCodeProd("AbduOziz"));
        System.out.println(isValideDecimal("24"));
        System.out.println(isValideDecimal("01"));
        System.out.println(isValideNumSecOrNumCni("95486545487567"));
    }

    public static boolean isValideDecimal(String val) {
        String regExp = "^[0-2][1-5]";
        return val.matches( regExp );
    }

    public static boolean isValideNumSecOrNumCni(String val) {
        String regExp = "^[1-9][0-9]{6}";
         if (val.length()==13){
           regExp = "^[1-9][0-9]{12}";
        }
        return val.matches( regExp );
    }

}
