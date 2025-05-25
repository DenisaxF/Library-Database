import java.sql.*;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        DatabaseConnection dbConnection = new DatabaseConnection();
        try (Connection con = dbConnection.getConnection()) {
            if (con != null && !con.isClosed()) {
                System.out.println(" Conexiune reușită la baza de date!");
            }
        } catch (SQLException e) {
            System.out.println(" Eroare la conectarea la baza de date:");
            e.printStackTrace();
        }

        BibliotecaService service = new BibliotecaService();

        try {
            /*service.getAllItems();
            service.afiseazaItems();
            Autor autor = new Autor("Maas", "Sarah J.", "americana", 1);

            Item item = new Carte("Queen of shadows", 0, autor, Categorie.FANTASY);

            service.adaugaItem(item);*/
            Autor autor1 = new Autor("Martin", "Alex K.", "olandeza", 5);
            Item item1 = new Comic(0, "Spider-Man: Homecoming", autor1);
            service.getAllItems();
            service.sorteazaItem();
            service.afiseazaCartiPeCategorie();
        }catch (SQLException e) {
            e.printStackTrace();
        }

    }
}