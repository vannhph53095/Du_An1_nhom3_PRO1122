package fpoly.ph53095.nhom3_du_an_1_pro1122.entity;

public class User {
    private String email;
    private String goidachon;
    private long sotien;
    private boolean hasPaid;
    private String timePaid;



    public User(String email, String goidachon, String timePaid) {
        this.email = email;
        this.goidachon = goidachon;
        this.timePaid = timePaid;
    }

    public String getEmail() {
        return email;
    }

    public String getGoidachon() {
        return goidachon;
    }

    public long getSotien() {
        return sotien;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }

    public String getTimePaid() {
        return timePaid;
    }
}
