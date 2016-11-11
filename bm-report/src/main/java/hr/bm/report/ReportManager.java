package hr.bm.report;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;

public class ReportManager {

	private static String SUFIX_ODT = ".odt";
	private static String PREFIX_ODT = "/META-INF/reports/";

	protected byte[] generatePdf(final AbstractReport p_report) {
		byte[] outputFile = null;
		try {
			outputFile = generateReport(p_report.getTemplate(), p_report.getReportData());
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

		return outputFile;
	}

	public byte[] generateReport(final String p_reportName, final Map<?, ?> p_reportData) {

		final String templatePath = PREFIX_ODT + p_reportName + SUFIX_ODT;
		DocumentTemplate template;
		final DocumentTemplateFactory templateFactory = new DocumentTemplateFactory();

		templateFactory.getFreemarkerConfiguration().setEncoding(new Locale("en_HR"), "UTF-8");

		final DocumentConversionService conversionService = new DocumentConversionServiceJod("PBZ");
		conversionService.addOdfTemplateProcessors(new RepeatCommandProcessor());

		final File documentFromTemplate = new File(ReportingFileUtil.createFileName("tmp-template", "odt"));
		final File outputDocument = new File(ReportingFileUtil.createFileName("tmp-out", "pdf"));

		InputStream inputStream = null;
		FileOutputStream fileOutputStream = null;

		try {
			 final ClassPathResource classpathResource = new ClassPathResource(templatePath);

			inputStream = classpathResource.getInputStream();
//			inputStream = new FileInputStream("src/main/java/hr/bm/report/" + templatePath);
			inputStream = new ByteArrayInputStream(conversionService.applyCommands(IOUtils.toByteArray(inputStream)));

			fileOutputStream = new FileOutputStream(documentFromTemplate);

			template = templateFactory.getTemplate(inputStream);
			template.createDocument(p_reportData, fileOutputStream);
			conversionService.convert(documentFromTemplate, outputDocument);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		} finally {
			ReportingFileUtil.closeStream(inputStream);
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		final byte[] outputBytes = ReportingFileUtil.load(outputDocument);
		ReportingFileUtil.delete(documentFromTemplate, outputDocument);
		return outputBytes;
	}
}
