package hse.se.aaizmaylov.petrinetslibrary.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
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

    @Contract("null, _ -> null")
    @Nullable
    public static Element getElementByTag(@Nullable NodeList list, @NotNull String tag) {
        if (list == null)
            return null;

        for (int i = 0, length = list.getLength(); i < length; i++) {
            if (!(list.item(i) instanceof Element))
                continue;

            Element el = (Element) list.item(i);
            if (el.getTagName().equals(tag)) {
                return el;
            }
        }

        return null;
    }
}
