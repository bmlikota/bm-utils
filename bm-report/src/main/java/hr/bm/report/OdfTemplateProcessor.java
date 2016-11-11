package hr.bm.report;

import org.odftoolkit.odfdom.doc.OdfTextDocument;

public interface OdfTemplateProcessor {

	void process(final OdfTextDocument p_document) throws Exception;

}
