import dao.AccesBD;
import serveur.Serveur;

import java.sql.SQLException;

public class Principale {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
       new Serveur().start();
    }
}
