package hr.bm.report;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public final class ReportingFileUtil {

    public static String createFileName(final String p_object, final String p_extension) {
    	String uuid = UUID.randomUUID().toString();
        return String.format("%s_%s.%s", p_object, uuid, p_extension);
    }

    public static byte[] load(final File p_file) {
        try {
            return load(new FileInputStream(p_file));
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public static byte[] load(final InputStream p_inputStream) {
        try {
            final ByteArrayOutputStream bout = new ByteArrayOutputStream();
            final byte[] readBuf = new byte[512 * 1024];

            int readCnt = p_inputStream.read(readBuf);

            while (readCnt > 0) {
                bout.write(readBuf, 0, readCnt);
                readCnt = p_inputStream.read(readBuf);
            }

            p_inputStream.close();

            return bout.toByteArray();
        } catch (IOException e) {
            return new byte[0];
        }
    }
    

    public static boolean delete(final File... p_files) {
    	boolean ret = true;
    	
    	for(File file:p_files) {
    		ret = ret && file.delete();
    	}
    	
    	return ret;
    }
    
    public static void closeStream(Closeable stream) {
      
      try {
        if(stream != null) {
          stream.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

}
