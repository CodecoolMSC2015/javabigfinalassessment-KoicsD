package tools;

import java.util.Set;

public interface HtmlCompatible {
	public String toHtmlString();
	public String toHtmlString(Set<String> skillNamesToHighlight);
}
