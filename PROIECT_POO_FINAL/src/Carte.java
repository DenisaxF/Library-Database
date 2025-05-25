public class Carte implements Item{

    private  String titlu;
    private  final int id;
    private  Autor autor;
    private  Categorie categorie;

    public Carte(String titlu, int id, Autor autor, Categorie categorie) {
        this.titlu = titlu;
        this.id = id;
        this.autor = autor;
        this.categorie = categorie;
    }

    public void setTitlu(String titlu){
        this.titlu = titlu;
    }

    public void setCategorie(Categorie categorie){
        this.categorie = categorie;
    }
    @Override
    public String getTitlu() {
        return titlu;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Autor getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return "Carte{" +
                 titlu + " de " + autor.getNumeComplet() + '\''+
                ", id='" + id + '\'' +
                ", categorie=" + categorie +
                '}';
    }


    public Categorie getCategorie() {
        return categorie;
    }


}
