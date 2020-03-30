public class TextHandle {
    public final int lenght;
    private String text;

    public TextHandle(String text) {
        this.text = text;
        this.lenght = text.length();
    }

    public char get(int index) {
        return text.charAt(index);
    }
}
