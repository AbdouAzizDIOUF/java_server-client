package serveur;

import dao.AccesBD;
import entity.Achat;
import entity.Produit;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class Serveur extends Thread{
    private int nbrClient=0;



    @Override
    public void run(){
        try {
            ServerSocket serversocket  = new ServerSocket(8888);
            System.out.println("Demarrage du serveur ..............");
            while (true) {
                Socket socket = serversocket.accept();
                new Service(socket, nbrClient).start();
                ++nbrClient;
                System.out.println("le numero client est : "+nbrClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    class Service extends Thread
    {
        private Socket sock;
        private int numeroClient;
        private AccesBD bdd;

        public Service(Socket sock, int numeroClient){
            this.sock = sock;
            this.numeroClient = numeroClient;
            bdd = new AccesBD();
        }

        @Override
        public void run()
        {
            boolean isOkay = false;
            try {

                ObjectInputStream objectInputStream = new ObjectInputStream(sock.getInputStream());
                ObjectOutputStream objectOutputStream=new ObjectOutputStream(sock.getOutputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(sock.getOutputStream());
                DataInputStream dataInputStream = new DataInputStream(sock.getInputStream());
                String IP = sock.getRemoteSocketAddress().toString();
                System.out.println("Connexion du client IP : "+IP);
                do {
                    String username = (String) objectInputStream.readObject();
                    System.out.println("username : "+username);
                    String password = (String) objectInputStream.readObject();
                    System.out.println("password : "+password);
                    isOkay = bdd.authentifier(username, password);
                    objectOutputStream.writeObject(username);
                    objectOutputStream.writeObject(password);

                    objectOutputStream.flush();
                }while (!isOkay);
                Produit p;
                while (true){
                    int status;
                    do {
                        String codeProduct = (String) objectInputStream.readObject();
                        System.out.println(codeProduct);
                        p = bdd.findProductByCode(codeProduct);
                        objectOutputStream.writeObject(p);
                        if (p!=null){
                            status=20;
                        }else {
                            status = 1;
                        }
                        System.out.println(status);
                    }while (status==1);

                    Achat achat = (Achat) objectInputStream.readObject();
                    System.out.println("idPod: "+achat.getProduit() +"\n"+
                            "prixTotale: "+achat.getPrixTotal()+"\n"+
                            "quantity: "+achat.getQuantity());

                    String result = bdd.saveAchat(achat.getProduit(), achat.getPrixTotal(), achat.getQuantity());
                    if ("Okay".equals(result)){
                        System.out.println(result);
                        objectOutputStream.writeObject(result);
                    }
                }
            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
