package hr.bm.report;

import java.awt.Color;
import com.lowagie.text.Font;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.odftoolkit.odfdom.converter.core.ODFConverterException;
import org.odftoolkit.odfdom.converter.pdf.PdfConverter;
import org.odftoolkit.odfdom.converter.pdf.PdfOptions;
import org.odftoolkit.odfdom.doc.OdfTextDocument;

import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfWriter;

import fr.opensagres.xdocreport.itext.extension.IPdfWriterConfiguration;
import fr.opensagres.xdocreport.itext.extension.font.IFontProvider;

public class DocumentConversionServiceImpl implements DocumentConversionService {

	private static final String BANK_ALEX = "ALEX";

	public DocumentConversionServiceImpl(List<OdfTemplateProcessor> processors) {
		super();
		this.processors = processors;
	}

	public DocumentConversionServiceImpl(String bank) {
		super();
		this.bank = bank;
		this.processors = new ArrayList<OdfTemplateProcessor>();
	}

	public DocumentConversionServiceImpl() {
		this.processors = new ArrayList<OdfTemplateProcessor>();
	}

	protected List<OdfTemplateProcessor> processors;
	protected OdfTextDocument document;
	protected String bank;

	public void convert(File p_from, File p_to) throws ODFConverterException, IOException {
		PdfOptions options = PdfOptions.create();

		if (BANK_ALEX.equals(bank)) {
			options.fontProvider(getFontProvider("/META-INF/reports/fonts/HelveticaNeueLTArabic-Light.ttf"));
		} else {
			options.fontProvider(getFontProvider("/META-INF/reports/OpenSans-Regular.ttf"));
		}

		document = null;
		InputStream in = new FileInputStream(p_from);
		try {
			document = OdfTextDocument.loadDocument(in);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		if (isRTL()) {
			options.setConfiguration(getPdfWriterConfiguration());
			options.setRtl(true);
		} else {
			options.setRtl(false);
		}

		OutputStream out = new FileOutputStream(p_to);
		PdfConverter.getInstance().convert(document, out, options);
		document.close();
		out.close();
		in.close();
	}

	public void convert(String p_fileNameFrom, String p_fileNameTo) throws Exception {
		final File inputFile = new File(p_fileNameFrom);
		final File outputFile = new File(p_fileNameTo);

		convert(inputFile, outputFile);
	}

	public byte[] applyCommands(byte[] byteArray) throws Exception {
		document = OdfTextDocument.loadDocument(new ByteArrayInputStream(byteArray));
		final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		if (processors != null) {
			for (final OdfTemplateProcessor processor : processors) {
				processor.process(document);
			}
			document.save(outputStream);
		}
		document.close();
		return outputStream.toByteArray();
	}

	public void addOdfTemplateProcessors(final OdfTemplateProcessor... p_templateProcessors) {
		for (final OdfTemplateProcessor processor : p_templateProcessors) {
			processors.add(processor);
		}
	}

	private IFontProvider getFontProvider(final String path) {
		return new IFontProvider() {
			public Font getFont(String familyName, String encoding, float size, int style, Color color) {
				try {
					BaseFont bf = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
					Font font = new Font(bf, size, style, color);
					if (familyName != null) {
						font.setFamily(familyName);
					}
					return font;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	private IPdfWriterConfiguration getPdfWriterConfiguration() {
		return new IPdfWriterConfiguration() {
			public void configure(final PdfWriter writer) {
				writer.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
				writer.addViewerPreference(PdfName.DIRECTION, PdfName.R2L);
			}
		};
	}

	protected Boolean isRTL() {
//		if (localeCode.contains("ar")) {
//			return true;
//		}
		return false;
	}

}
