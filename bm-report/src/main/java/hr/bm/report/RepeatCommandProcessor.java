package hr.bm.report;

import java.util.HashMap;
import java.util.Map;

import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.dom.OdfContentDom;
import org.odftoolkit.odfdom.dom.element.text.TextScriptElement;
import org.odftoolkit.odfdom.dom.element.text.TextSpanElement;
import org.odftoolkit.odfdom.incubator.search.TextNavigation;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.w3c.dom.Node;

public class RepeatCommandProcessor implements OdfTemplateProcessor {

	public void process(OdfTextDocument p_document) throws Exception {

		final OdfContentDom contentDom = p_document.getContentDom();
		final TextNavigation tableStart = new TextNavigation("@repeat:((\\(.*\\))|(\\w*)):\\w*", p_document);

		final HashMap<OdfElement, TextSpanElement> replacements = new HashMap<OdfElement, TextSpanElement>();

		while (tableStart.hasNext()) {
			final OdfElement tableStartElem = tableStart.getCurrentItem().getElement();
			final String[] tableDefinition = tableStartElem.getTextContent().split(":");

			String definition = tableDefinition[1];
			if ((definition.charAt(0) == '(') && (definition.charAt(definition.length() - 1) == ')')) {
				definition = definition.substring(1, definition.length() - 1);
			}
			final String scritpDefinition = "@table:table-row\n[#list " + definition + " as " + tableDefinition[2]
					+ "]\n@/table:table-row\n[/#list]";

			final TextSpanElement span = new TextSpanElement(contentDom);
			final TextScriptElement script = new TextScriptElement(contentDom);

			script.setScriptLanguageAttribute("JOOScript");
			script.setTextContent(scritpDefinition);
			span.appendChild(script);

			replacements.put(tableStartElem, span);
		}

		for (final Map.Entry<OdfElement, TextSpanElement> entry : replacements.entrySet()) {
			final OdfElement toBeReplaced = entry.getKey();
			final TextSpanElement replacement = entry.getValue();
			final Node parentNode = toBeReplaced.getParentNode();

			parentNode.replaceChild(replacement, toBeReplaced);
		}
	}
}