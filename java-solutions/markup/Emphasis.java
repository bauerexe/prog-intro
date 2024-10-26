package markup;

import java.util.List;

public class Emphasis extends AbstractMarkdown {
    public Emphasis(List<MarkDown> list) {
        super(list, "*", "i");
    }
}