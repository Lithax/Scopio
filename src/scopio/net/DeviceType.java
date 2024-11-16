package scopio.net;
public enum DeviceType {
    PC(""),
    LAPTOP(""),
    MOBILE(""),
    SERVER(""),
    ROUTER(""),
    UNKNOWN("");

    private final String imgpath;

    DeviceType(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getImgpath() {
        return imgpath;
    }
}
