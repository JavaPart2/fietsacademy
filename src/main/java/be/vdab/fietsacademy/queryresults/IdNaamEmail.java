package be.vdab.fietsacademy.queryresults;

public class IdNaamEmail {
    private final long id;
    private final String naam;
    private final String emailAdres;

    public IdNaamEmail(long id, String naam, String emailAdres) {
        this.id = id;
        this.naam = naam;
        this.emailAdres = emailAdres;
    }

    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getEmailAdres() {
        return emailAdres;
    }
}
