package markup;

import java.util.List;

public class Paragraph extends AbstractMarkdown {
    private List<MarkDown> list;

    public Paragraph(List<MarkDown> list) {
        super(list, "", "");
        this.list = list;
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        for (MarkDown i : list) {
            i.toBBCode(sb);
        }
    }
}
