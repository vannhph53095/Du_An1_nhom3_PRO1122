package fpoly.ph53095.nhom3_du_an_1_pro1122.entity;

public class PhimMoi {
    private String title;
    private int image;

    public PhimMoi(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
