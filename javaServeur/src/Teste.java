import dao.AccesBD;
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

    static Scanner sc = new Scanner(System.in);
    static SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

    public static void main(String[] args) throws SQLException, ParseException {

        //System.out.println(new Date().getTime());
        AccesBD bdd = new AccesBD();
        String username;
        String password;
        boolean estOk = false;
        String dateAchat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(new java.util.Date());
        System.out.println(dateAchat);
        //Date date = formatter.parse(dateAchat);
        final java.sql.Date dateSQL = new java.sql.Date(new Date().getTime()) ;
        System.out.println(dateSQL);
        //System.out.println(new Date());
        do {
            System.out.print("donner le username : ");
            username = sc.next();
            System.out.print("donner le password : ");
            password = sc.next();
            estOk = bdd.authentifier(username, password);
            if (!estOk){
                System.out.println("identifiant invalide");
            }
        }while (!estOk);
                //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                //System.out.println(timestamp);

            String ok;
            do{
                Produit p;
                String codeProd;
                System.out.println("****************************************************************************");
                System.out.println("               Bienvenue dans votre espace personnel                        ");
                System.out.println("****************************************************************************");
                System.out.print("Veillez saisir le code du produit : ");
                codeProd = sc.next();
                p = bdd.findProductByCode(codeProd);
                if (p!=null) {
                    p.detailAchat();
                }else {
                    System.out.println("Produit non disponible");
                }
                System.out.println("Recherchez-vous un autre produit, Tapez oui :");
                ok = sc.next().toLowerCase();
            }while ("oui".equals(ok));
    }
}
