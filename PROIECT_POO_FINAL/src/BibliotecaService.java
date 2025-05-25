import java.util.*;
import java.io.*;
import java.sql.*;

public class BibliotecaService {

    List<Item> list = new ArrayList<>();
    Map<Categorie, List<Carte>> cartiPeCategorie = new EnumMap<>(Categorie.class);
    DatabaseConnection dbCon = new DatabaseConnection();
    AuditService auditService = new AuditService();

    public void getAllItems() throws SQLException{
        auditService.fileWriter("getAllItems");
        String sql = "SELECT i.iditem, i.titlu, i.tip, i.categorie, i.idautor, a.nume, a.prenume, a.nationalitate"+
                     " FROM ITEM i "+
                     " JOIN AUTORI a ON i.idautor = a.idautori";

        try(Connection con = dbCon.getConnection();){
            PreparedStatement pstmt = con.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("iditem");
                String titlu = rs.getString("titlu");
                String categoriestr = rs.getString("categorie");
                Categorie categorie = null;
                if(categoriestr != null){
                    categorie = Categorie.valueOf(categoriestr.toUpperCase());
                }
                String tip = rs.getString("tip");

                String nume = rs.getString("nume");
                String prenume = rs.getString("prenume");
                String nationalitate = rs.getString("nationalitate");
                int idAutor = rs.getInt("idautor");

                Autor autor = new Autor(nume, prenume, nationalitate, idAutor);

                Item item;
                if(tip.equals("Carte"))
                {
                    Carte carte = new Carte(titlu, id, autor, categorie);
                    item = carte;
                    cartiPeCategorie.computeIfAbsent(categorie, k -> new ArrayList<>()).add(carte);
                }
                else
                {
                    item = new Comic(id, titlu, autor);
                }
                list.add(item);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void adaugaItem(Item item) throws DuplicateException, SQLException {
        auditService.fileWriter("adaugaItem");

        for(Item i : list){
            if(i.getId() == item.getId()){
                throw new DuplicateException(item.getId());
            }
        }

        String sql = "INSERT INTO ITEM(titlu, tip, categorie, idautor) VALUES(?,?,?,?)";

        try(Connection con = dbCon.getConnection())
        {
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, item.getTitlu());

            if(item instanceof Carte) {
                pstmt.setString(2, "Carte");
                pstmt.setString(3, ((Carte) item).getCategorie().toString());
            }
            else {
                pstmt.setString(2, "Comic");
                pstmt.setNull(3, java.sql.Types.VARCHAR);

            }

            pstmt.setInt(4, item.getAutor().getIdAutor());
            pstmt.executeUpdate();

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if(generatedKeys.next()){
                int generatedId = generatedKeys.getInt(1);
                Item itemCuId;
                if (item instanceof Carte carte) {
                    itemCuId = new Carte(carte.getTitlu(), generatedId, carte.getAutor(), carte.getCategorie());
                } else {
                    itemCuId = new Comic(generatedId, item.getTitlu(), item.getAutor());
                }

                // Adaugi în listă și hartă
                list.add(itemCuId);

                if (itemCuId instanceof Carte carteCuId) {
                    cartiPeCategorie
                            .computeIfAbsent(carteCuId.getCategorie(), k -> new ArrayList<>())
                            .add(carteCuId);
                }

                System.out.println("Item adăugat cu ID generat: " + generatedId);
            }


        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void stergeItemsOlandeze() throws SQLException{
        auditService.fileWriter("stergeItemsOlandeze");
         String sql = "DELETE FROM ITEM" +
                      " WHERE idautor IN ( "+
                      " SELECT idautori "+
                      " FROM AUTORI " +
                      " WHERE nationalitate = 'olandeza' "+
                   ")";
         try(Connection con = dbCon.getConnection()){
             PreparedStatement smt = con.prepareStatement(sql);
             int rowsAffected = smt.executeUpdate();
             System.out.println("Randuri sterse: " + rowsAffected);
             list.removeIf(i -> "olandeza".equalsIgnoreCase(i.getAutor().getNationalitate()));

         }catch(SQLException e){
             e.printStackTrace();
         }
    }

    public void prefixeazaFantasy() throws SQLException{
        auditService.fileWriter("prefixeazaFantasy");
        String sql = "UPDATE ITEM SET titlu = CONCAT('[FANTASY]', titlu) WHERE tip = 'Carte' AND categorie = 'Fantasy'";
        try(Connection con = dbCon.getConnection()){
            PreparedStatement psmt = con.prepareStatement(sql);
            int rowsModificate = psmt.executeUpdate();
            System.out.println("Randuri Fantasy: " + rowsModificate);
            for(Item i : list){
                if(i instanceof Carte && ((Carte) i).getCategorie().equals(Categorie.FANTASY))
                {
                    ((Carte) i).setTitlu("[FANTASY]" + i.getTitlu());
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void sorteazaItem(){
        Collections.sort(list);
    }

    public void afiseazaItems()
    {
        list.forEach(System.out::println);
    }

    public void afiseazaCartiPeCategorie() {
        for (Map.Entry<Categorie, List<Carte>> entry : cartiPeCategorie.entrySet()) {
            System.out.println("Categorie: " + entry.getKey());
            for (Carte carte : entry.getValue()) {
                System.out.println("  - " + carte.getTitlu());
            }
        }
    }
}
