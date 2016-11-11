package hr.bm.report;

import java.io.File;
import java.io.IOException;

import org.odftoolkit.odfdom.converter.core.ODFConverterException;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class DocumentConversionServiceJod extends DocumentConversionServiceImpl {

	public DocumentConversionServiceJod(final String bank) {
		super(bank);
	}

	private final int OPEN_OFFICE_SERVICE_PORT = 8100;

	public void convert(File p_from, File p_to) throws ODFConverterException, IOException {

		OpenOfficeConnection connection = new SocketOpenOfficeConnection(OPEN_OFFICE_SERVICE_PORT);
		if (!connection.isConnected()) {
			super.convert(p_from, p_to);
			return;
		}

		connection.connect();

		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		converter.convert(p_from, p_to);

		connection.disconnect();

	}

}
