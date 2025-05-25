public class Comic implements Item{

    private  final int id;
    private  String titlu;
    private  Autor autor;

    public Comic(int id, String titlu, Autor autor) {
        this.id = id;
        this.titlu = titlu;
        this.autor = autor;
    }
    public void setTitlu(String titlu){
        this.titlu = titlu;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getTitlu() {
        return titlu;
    }

    @Override
    public Autor getAutor() {
        return autor;
    }

    @Override
    public String toString() {
        return "Comic{" +
                titlu + " de " + autor.getNumeComplet() + '\''+
                ", id='" + id + '\'' +
                '}';
    }


}
