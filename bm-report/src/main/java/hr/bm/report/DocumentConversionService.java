package hr.bm.report;

import java.io.File;

public interface DocumentConversionService {

	/**
	 * Converts a given input document file to another document format and
	 * writes the result into the given output document file. The document
	 * formats are determined from the file extensions.
	 * 
	 * @throws Exception
	 **/
	void convert(final File p_from, final File p_to) throws Exception;

	/**
	 * Converts a given input document file to another document format and
	 * writes the result into the given output document file. The document
	 * formats are determined from the file extensions.
	 * 
	 * @throws Exception
	 **/
	void convert(final String p_fileNameFrom, final String p_fileNameTo) throws Exception;

	byte[] applyCommands(byte[] byteArray) throws Exception;

	void addOdfTemplateProcessors(OdfTemplateProcessor... p_templateProcessors);

}