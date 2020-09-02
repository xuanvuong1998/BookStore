/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.HashMap;
import java.util.Map;
import utils.xmlchecker.XmlSyntaxChecker;

public class TextUtils {

    private static final Map<String, String> UNICODE_MAP;
    private static final Map<String, String> UNICODE_LINK_MAP;

    static {
        UNICODE_MAP = new HashMap<>();
        UNICODE_MAP.put("&Agrave;", "À");
        UNICODE_MAP.put("&Aacute;", "Á");
        UNICODE_MAP.put("&Acirc;", "Â");
        UNICODE_MAP.put("&Atilde;", "Ã");
        UNICODE_MAP.put("&agrave;", "à");
        UNICODE_MAP.put("&aacute;", "á");
        UNICODE_MAP.put("&acirc;", "â");
        UNICODE_MAP.put("&atilde;", "ã");
        UNICODE_MAP.put("&Egrave;", "È");
        UNICODE_MAP.put("&Eacute;", "É");
        UNICODE_MAP.put("&Ecirc;", "Ê");
        UNICODE_MAP.put("&egrave;", "è");
        UNICODE_MAP.put("&ecirc;", "ê");
        UNICODE_MAP.put("&eacute;", "é");
        UNICODE_MAP.put("&Igrave;", "Ì");
        UNICODE_MAP.put("&Iacute;", "Í");
        UNICODE_MAP.put("&igrave;", "ì");
        UNICODE_MAP.put("&iacute;", "í");
        UNICODE_MAP.put("&Ograve;", "Ò");
        UNICODE_MAP.put("&Oacute;", "Ó");
        UNICODE_MAP.put("&Ocirc;", "Ô");
        UNICODE_MAP.put("&ograve;", "ò");
        UNICODE_MAP.put("&oacute;", "ó");
        UNICODE_MAP.put("&ocirc;", "ô");
        UNICODE_MAP.put("&otilde;", "õ");
        UNICODE_MAP.put("&Ugrave;", "Ù");
        UNICODE_MAP.put("&Uacute;", "Ú");
        UNICODE_MAP.put("&ugrave;", "ù");
        UNICODE_MAP.put("&uacute;", "ú");
        UNICODE_MAP.put("&Yacute;", "Ý");
        UNICODE_MAP.put("&yacute;", "ý");
        UNICODE_MAP.put("&amp;", "&");

        UNICODE_LINK_MAP = new HashMap<>();
        UNICODE_LINK_MAP.put("&ndash;", "–");
    }

    public static String refineHtml(String src) {
        src = removeMiscTags(src);

        XmlSyntaxChecker xmlSyntaxChecker = new XmlSyntaxChecker();
        src = xmlSyntaxChecker.check(src);

        return src;
    }

    public static String removeMiscTags(String src) {
        String result = src;

        String expression = "<script.*?</script>";
        result = result.replaceAll(expression, "");

        expression = "<!--.*?-->";
        result = result.replaceAll(expression, "");

        expression = "&nbsp;?";
        result = result.replaceAll(expression, "");

        expression = "&#8211;?";
        result = result.replaceAll(expression, "-");

        expression = "&#038;?";
        result = result.replaceAll(expression, "&");

        return result;
    }

    public static String parseUnicode(String src) {
        if (src == null) {
            return "";
        }
        String result = src;
        for (Map.Entry<String, String> entry : UNICODE_MAP.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static String parseUnicodeLink(String src) {
        if (src == null) {
            return "";
        }
        String result = src;
        for (Map.Entry<String, String> entry : UNICODE_LINK_MAP.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
