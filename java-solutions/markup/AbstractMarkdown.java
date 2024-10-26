package markup;

import java.util.List;

public abstract class AbstractMarkdown implements MarkDown {
    private final List<MarkDown> list;
    private final String symbols;
    private final String symbols1;

    public AbstractMarkdown(List<MarkDown> list, String symbols, String symbols1) {
        this.list = list;
        this.symbols = symbols;
        this.symbols1 = symbols1;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(symbols);
        for (MarkDown i : list) {
            i.toMarkdown(sb);
        }
        sb.append(symbols);
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        sb.append("[" + symbols1 + "]");
        for (MarkDown i : list) {
            i.toBBCode(sb);
        }
        sb.append("[/" + symbols1 + "]");
    }
}