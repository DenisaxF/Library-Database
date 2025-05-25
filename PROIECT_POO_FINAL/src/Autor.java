public record Autor(String nume, String prenume, String nationalitate, int idAutor) {

    public String getNumeComplet(){
        return nume + " " + prenume;
    }

    public String getNationalitate()
    {
        return nationalitate;
    }

    public int getIdAutor() {return idAutor;}
}
