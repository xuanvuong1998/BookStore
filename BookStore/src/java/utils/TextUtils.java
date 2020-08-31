/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import utils.xmlchecker.XmlSyntaxChecker;


/**
 *
 * @author NhanTT
 */
public class TextUtils {
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
}
