package dao;

import entity.Achat;
import entity.Produit;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author abdou
 */
public class AccesBD {

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public AccesBD(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://188.166.203.48:5432/projet", "projet", "projet");
            System.out.println("connexion a la base de donnees etablie avec succee");
        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /**
     * Cette fonction verifie l'identitie de l'utilisateur
     * @param username le login de l'utilisateur
     * @param motDePasse le mot de passe de l'utilisateur
     * @return Boolean
     * @throws SQLException
     */
    public Boolean authentifier(String username, String motDePasse) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT username, password2 FROM utlisaateur WHERE username=? AND password2=?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, motDePasse);
        resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    /**
     * Cette fonction permet de trouver un produit donnees suivant son code bar du produit
     * @param code
     * @return
     */
    public Produit findProductByCode(String code){
        Produit produit = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT id, code_prod, nom, prix_prod, type_produit FROM produit WHERE code_prod=?");
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                int id = resultSet.getInt("id");
                int prix = resultSet.getInt("prix_prod");
                String codeProd = resultSet.getString("code_prod");
                String nomProd = resultSet.getString("nom");
                String typeProd = resultSet.getString("type_produit");

                produit = new Produit(id,prix,nomProd,typeProd,codeProd);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return produit;
    }

    /**
     *
     * @param idProd
     * @param idClient
     * @return
     */
    public Boolean validateAchat(int[] idProd, int idClient){
        for (int i=0; i<idProd.length; i++){
            int idCourant = idProd[i];

        }
        return true;
    }

    /**
     *
     * @param idProd
     * @param idClient
     * @return
     * @throws SQLException
     */
    private Boolean save(int idProd, int idClient) throws SQLException {
        String dateAchat = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
        preparedStatement = connection.prepareStatement("INSERT INTO achat (produit_id, client_id, purchase_at) VALUES (?, ?, ?)");
        preparedStatement.setInt(1, idProd);
        preparedStatement.setInt(2, idClient);
        preparedStatement.setString(3, dateAchat);

        int x = preparedStatement.executeUpdate();
        return x == 1;
    }

    /**
     *
     * @param prixTotal
     * @param qte
     * @return
     * @throws SQLException
     */
    public String saveAchat(int idProd,float prixTotal, int qte) throws SQLException {
        preparedStatement = connection.prepareStatement("INSERT INTO achat (quantite, create_at, prix_total, produit_id) VALUES (?, ?, ?, ?)");
        preparedStatement.setInt(1, qte);
        preparedStatement.setDate(2, new java.sql.Date(System.currentTimeMillis()));
        preparedStatement.setFloat(3, prixTotal);
        preparedStatement.setInt(4, idProd);

        int x = preparedStatement.executeUpdate();
        if (x==1){
            return "Okay";
        }
        return "NotOkay";
    }

    /**
     *
     * @param numSecSociele
     * @return
     * @throws SQLException
     */
    public boolean verifCarteVitale(String numSecSociele) throws SQLException {
        if (numSecSociele.length() == 10){
            preparedStatement = connection.prepareStatement("SELECT num_tel FROM personne WHERE num_tel = ?");
            preparedStatement.setString(1, numSecSociele);

            return preparedStatement.executeQuery().next();
        }else if(numSecSociele.length() == 13){
            preparedStatement = connection.prepareStatement("SELECT num_sec FROM personne WHERE num_sec = ?");
            preparedStatement.setString(1, numSecSociele);

            return preparedStatement.executeQuery().next();
        }

        return false;
    }
}
