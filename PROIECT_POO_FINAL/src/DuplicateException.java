public class DuplicateException extends ExceptieBaza {
    public DuplicateException(int id){
        super("Itemul cu id-ul " + id + " exista deja");
    }
}
