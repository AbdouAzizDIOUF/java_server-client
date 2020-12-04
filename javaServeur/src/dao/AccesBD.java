package dao;

import entity.Achat;
import entity.BilanVente;
import entity.Personne;
import entity.Produit;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
     *
     * @param username le login de l'utilisateur
     * @param motDePasse le mot de passe de l'utilisateur
     * @return Boolean
     * @throws SQLException
     */
    public Boolean authentifier(String username, String motDePasse) throws SQLException {
        preparedStatement = connection.prepareStatement("SELECT username, password2 FROM utlisaateur WHERE username=? AND password2=?");
        preparedStatement.setString(    1, username);
        preparedStatement.setString(2, motDePasse);
        resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    /**
     * Cette fonction permet de trouver un produit donnees suivant son code bar du produit
     *
     * @param code
     * @return
     */
    public Produit findProductByCode(String code){
        Produit produit = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT id, code_prod, nom, prix_prod, type_produit, stock FROM produit WHERE code_prod=?");
            preparedStatement.setString(1, code);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                int id = resultSet.getInt("id");
                int prix = resultSet.getInt("prix_prod");
                String codeProd = resultSet.getString("code_prod");
                String nomProd = resultSet.getString("nom");
                String typeProd = resultSet.getString("type_produit");
                int stock = resultSet.getInt("stock");

                produit = new Produit(id,prix,nomProd,typeProd,codeProd, stock);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return produit;
    }

    /**
     * Cette fonction permet de trouver un produit donnees suivant son code bar du produit
     *
     * @param idProd
     * @return
     */
    public Produit findProductById(int idProd){
        Produit produit = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT id, code_prod, nom, prix_prod, type_produit, stock FROM produit WHERE id=?");
            preparedStatement.setInt(1, idProd);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int prix = resultSet.getInt("prix_prod");
                String codeProd = resultSet.getString("code_prod");
                String nomProd = resultSet.getString("nom");
                String typeProd = resultSet.getString("type_produit");
                int stock = resultSet.getInt("stock");

                produit = new Produit(id,prix,nomProd,typeProd,codeProd, stock);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return produit;
    }


    /**
     *
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
     * Permet d'enregistrer une vente
     *
     * @param prixTotal
     * @param qte
     * @return
     * @throws SQLException
     */
    public String saveAchat(int idProd,float prixTotal, int qte) throws SQLException {
        decremStock(idProd, qte);
        preparedStatement = connection.prepareStatement("INSERT INTO achat (quantite, create_at, prix_total, produit_id) VALUES (?, ?, ?, ?)");
        preparedStatement.setInt(1, qte);
        preparedStatement.setDate(2, new java.sql.Date(System.currentTimeMillis()));
        preparedStatement.setFloat(3, prixTotal);
        preparedStatement.setInt(4, idProd);
        int x = preparedStatement.executeUpdate();

        if (x==1) {
            return "Okay";
        }
        return "NotOkay";
    }

    /**
     * Soustrait la quantite de produit vendu a la stock total du produit
     *
     * @param idProd
     * @param quantite
     * @throws SQLException
     */
    public void decremStock(int idProd, int quantite) throws SQLException {
        Produit p = findProductById(idProd);
        int delta = p.getStock()-quantite;
        preparedStatement = connection.prepareStatement("UPDATE produit SET stock = ? WHERE id=?");
        preparedStatement.setInt(1, delta);
        preparedStatement.setInt(2, idProd);

        preparedStatement.executeUpdate();
    }
    /**
     * Verifie l'existance d'un code de securite social valide
     * @param numSecSociele
     * @return
     * @throws SQLException
     */
    public Personne verifCarteVitale(String numSecSociele) throws SQLException {
        Personne personne = null;
        if (numSecSociele.length() == 7 || numSecSociele.length() == 13) {
            preparedStatement = connection.prepareStatement("SELECT id, nom, prenom, num_cni, num_sec FROM personne WHERE num_cni = ?");
            preparedStatement.setString(1, numSecSociele);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String numCni = resultSet.getString("num_cni");
                String num_sec = resultSet.getString("num_sec");

                personne = new Personne(id,nom,prenom,numCni,num_sec);
            }
            resultSet.close();

        }

        return personne;
    }

    /**
     * Cette fonctio fais le bilan global des produits vendus
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<BilanVente> bilanProductVendus() throws SQLException {
        ArrayList<BilanVente> bilanVentes = new ArrayList<>();

        preparedStatement = connection.prepareStatement("SELECT produit.nom, produit.type_produit, count(produit.code_prod) AS nbAchat, SUM(achat.prix_total) AS Total_Price, SUM(achat.quantite) AS Quantite_Totale FROM produit, achat WHERE produit.id=achat.produit_id GROUP BY achat.produit_id, produit.nom, produit.type_produit");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String nom = resultSet.getString("nom");
            String typeProd = resultSet.getString("type_produit");
            int nbVente = resultSet.getInt("nbAchat");
            float totalPrice = resultSet.getFloat("Total_Price");
            int qTotal = resultSet.getInt("Quantite_Totale");

            BilanVente bilanVente = new BilanVente(nom,typeProd,nbVente,totalPrice,qTotal);

            bilanVentes.add(bilanVente);
        }

        return bilanVentes;
    }


    /**
     * Cette fonction permette de lister les produit qui ont ete vendus aujord'hui
     *
     * @return
     * @throws SQLException
     */
    public ArrayList<BilanVente> bilanJournalierProductVendus() throws SQLException {
        ArrayList<BilanVente> bilanVentes = new ArrayList<>();
        java.sql.Date d = new java.sql.Date(System.currentTimeMillis());
        preparedStatement = connection.prepareStatement("SELECT produit.nom, produit.type_produit, count(produit.code_prod) AS nbAchat, SUM(achat.prix_total) AS Total_Price, SUM(achat.quantite) AS Quantite_Totale FROM produit, achat WHERE produit.id=achat.produit_id AND achat.create_at=date(now()) GROUP BY achat.produit_id, produit.nom, produit.type_produit");
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            String nom = resultSet.getString("nom");
            String typeProd = resultSet.getString("type_produit");
            int nbVente = resultSet.getInt("nbAchat");
            float totalPrice = resultSet.getFloat("Total_Price");
            int qTotal = resultSet.getInt("Quantite_Totale");

            BilanVente bilanVente = new BilanVente(nom,typeProd,nbVente,totalPrice,qTotal);

            bilanVentes.add(bilanVente);
        }

        return bilanVentes;
    }

}
