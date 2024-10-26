package markup;

public class Text implements MarkDown {
    String string;

    public Text(String string) {
        this.string = string;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(string);
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        sb.append(string);
    }
}
