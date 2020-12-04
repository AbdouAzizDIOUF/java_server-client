import dao.AccesBD;
import entity.BilanVente;
import entity.Produit;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * @author abdou
 */
public class Teste {

    static AccesBD bb = new AccesBD();
    static Scanner sc = new Scanner(System.in);
    static SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

    public static void main(String[] args) throws SQLException, ParseException {

       String username;
       String password;

        /*System.out.print("Username : "); username = sc.nextLine();
        System.out.print("Password : "); password = sc.nextLine();

        System.out.println(username +"\n "+password);
*/
        //System.out.println(bb.verifCarteVitale("6017890"));

        bb.bilanProductVendus().forEach(bilanVente -> System.out.println(bilanVente.toString()));
    }
}
