package hr.bm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

	public static DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public Date unmarshal(String date) throws Exception {
		return formatter.parse(date);
	}

	@Override
	public String marshal(Date date) throws Exception {
		return date.toString();
	}

}
