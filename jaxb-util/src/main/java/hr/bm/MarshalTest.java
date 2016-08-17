package hr.bm;

import java.io.File;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class MarshalTest {

	public static void main(String[] args) throws Exception {
		marshall();
		unmarshall();
		marshallXsdValidation();
		marshallAndValidate();
	}

	private static void unmarshall() throws Exception {

//		File file = new File( "F:/Branko/USBBackup/Java/Projects/git/bm-utils/jaxb-util/src/main/java/hr/bm/countries.xml" );
		File file2 = new File("C:/Users/bmlikota/Documents/Projects/git/bm-utils/jaxb-util/src/main/java/hr/bm/countries.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Country.class);
		/**
		* the only difference with the marshaling operation is here
		*/
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Country countres = (Country) jaxbUnmarshaller.unmarshal(file2);
		System.out.println("\n" + countres);
	}

	private static void marshallAndValidate() throws Exception {

		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sf.newSchema(new File("C:/Users/bmlikota/Documents/Projects/git/bm-utils/jaxb-util/src/main/java/hr/bm/countries.xsd"));

		JAXBContext jaxbContext = JAXBContext.newInstance(Country.class);

		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setSchema(schema);

		// the schema uses a validation handler for validate the objects
		marshaller.setEventHandler(new MyValidationEventHandler());

		/**
		 * validation will fail because continent is mandatory
		 */
		Country spain = new Country();
		spain.setName("Spain");
		spain.setCapital("Madrid");
		// spain.setFoundation( LocalDate.of( 1469, 10, 19 ) );
//		try {
//			marshaller.marshal(spain, System.out);
//		} catch (JAXBException ex) {
//			ex.printStackTrace();
//		}

		/**
		 * continent is wrong and validation will fail
		 */
		Country australia = new Country();
		australia.setName("Australia");
		australia.setCapital("Camberra");
		// australia.setFoundation( LocalDate.of( 1788, 01, 26 ) );
		australia.setContinent("Australia");
//		try {
//			marshaller.marshal(australia, System.out);
//		} catch (JAXBException ex) {
//			ex.printStackTrace();
//		}

		/**
		 * finally everything ok
		 */
		australia = new Country();
		australia.setName("Australia");
		australia.setCapital("Camberra");
		// australia.setFoundation( LocalDate.of( 1788, 01, 26 ) );
		australia.setContinent("Oceania");
		try {
			marshaller.marshal(australia, System.out);
		} catch (JAXBException ex) {
			ex.printStackTrace();
		}
	}

	private static void marshallXsdValidation() throws Exception {
		/**
		* error will be thrown because continent is mandatory
		*/
		Country spain = new Country();
		spain.setName("Spain");
		spain.setCapital("Madrid");
		spain.setContinent("Oceania");
//		spain.setFoundation(DateAdapter.formatter.parse("16-05-2016"));
//		spain.setPopulation(1000);
		spain.setImportance(1);

		/**
		* ok
		*/
		Country australia = new Country();
		australia.setName("Australia");
		australia.setCapital("Camberra");
		australia.setContinent("Oceania");
//		australia.setFoundation(DateAdapter.formatter.parse("16-05-2016"));
		australia.setPopulation(1000);
		australia.setImportance(1);

		/**
		* schema is created
		*/
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = sf.newSchema(new File("C:/Users/bmlikota/Documents/Projects/git/bm-utils/jaxb-util/src/main/java/hr/bm/countries.xsd"));

		/**
		* context is created and used to create sources for each country
		*/
		JAXBContext jaxbContext = JAXBContext.newInstance(Country.class);
		JAXBSource sourceSpain = new JAXBSource(jaxbContext, spain);
		JAXBSource sourceAustralia = new JAXBSource(jaxbContext, australia);
		/**
		* validator is initialized
		*/
		Validator validator = schema.newValidator();
		validator.setErrorHandler(new MyErrorHandler());

		//validator is used
		try {
			validator.validate(sourceSpain);
			System.out.println("spain has no problems");
		} catch (SAXException ex) {
			ex.printStackTrace();
			System.out.println("spain has problems");
		}
		try {
			validator.validate(sourceAustralia);
			System.out.println("australia has no problems");
		} catch (SAXException ex) {
			ex.printStackTrace();
			System.out.println("australia has problems");
		}
	}

	private static void marshall() throws Exception {
		Country hrvatska = new Country();
		hrvatska.setName( "Hrvatska" );
		hrvatska.setCapital( "Zagreb" );
		ArrayList<String> towns = new ArrayList<String>() {
			private static final long serialVersionUID = 1L;
			{
				add("Split");
				add("Varazdin");
			}
		};
		hrvatska.setFoundation(DateAdapter.formatter.parse("16-05-2016"));
		hrvatska.setTowns(towns);
		hrvatska.setImportance(1);
		hrvatska.setPopulation(1500000);
		/* init jaxb marshaler */
		JAXBContext jaxbContext = JAXBContext.newInstance(Country.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		/* set this flag to true to format the output */
		jaxbMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true);
		/* marshaling of java objects in xml (output to file and standard output) */
		jaxbMarshaller.marshal(hrvatska, new File("country.xml"));
		jaxbMarshaller.marshal(hrvatska, System.out);
	}

}
