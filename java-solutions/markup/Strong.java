package markup;

import java.util.List;

public class Strong extends AbstractMarkdown {
    public Strong(List<MarkDown> list) {
        super(list, "__", "b");
    }
}