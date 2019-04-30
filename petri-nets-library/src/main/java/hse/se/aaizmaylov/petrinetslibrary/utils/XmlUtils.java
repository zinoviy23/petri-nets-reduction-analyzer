package hse.se.aaizmaylov.petrinetslibrary.utils;

import org.xml.sax.Attributes;

public class XmlUtils {
    public static String attributesToList(Attributes attributes) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < attributes.getLength(); i++) {
            sb.append(attributes.getQName(i)).append("=\"").append(attributes.getValue(i)).append("\" ");
        }

        if (sb.length() > 0)
            sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
}
