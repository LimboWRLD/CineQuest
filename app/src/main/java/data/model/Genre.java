package data.model;

public class Genre {
    private int id;

    private String name;

    public Genre(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Genre() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
