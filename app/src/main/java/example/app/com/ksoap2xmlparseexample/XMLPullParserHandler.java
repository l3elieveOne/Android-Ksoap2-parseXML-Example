package example.app.com.ksoap2xmlparseexample;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XMLPullParserHandler {
    List<DataAccess> PTT_DS;
    private DataAccess dataAccess;
    private String text;

    public XMLPullParserHandler() {
        PTT_DS = new ArrayList<DataAccess>();
    }

    public List<DataAccess> getPTT_DS() {
        return PTT_DS;
    }

    public List<DataAccess> parse(String is) {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(new StringReader(is.toString()));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("DataAccess")) {
                            // create a new instance
                            dataAccess = new DataAccess();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("DataAccess")) {
                            // add object to list
                            PTT_DS.add(dataAccess);
                        } else if (tagname.equalsIgnoreCase("product")) {
                            dataAccess.setProduct(text);
                        } else if (tagname.equalsIgnoreCase("price")) {
                            dataAccess.setPrice(text);
                        }
                        break;

                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return PTT_DS;
    }
}