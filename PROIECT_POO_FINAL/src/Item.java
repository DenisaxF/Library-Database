public interface Item extends Comparable<Item> {
    String getTitlu();
    int getId();
    Autor getAutor();

    @Override
    default int compareTo(Item o) {
        return getTitlu().compareTo(o.getTitlu());
    }
}
