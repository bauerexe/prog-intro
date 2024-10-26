package markup;

import java.util.List;

public class Strikeout extends AbstractMarkdown {
    public Strikeout(List<MarkDown> list) {
        super(list, "~", "s");
    }
}