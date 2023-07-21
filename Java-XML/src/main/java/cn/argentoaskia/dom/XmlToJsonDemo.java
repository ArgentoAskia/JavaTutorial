package cn.argentoaskia.dom;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class XmlToJsonDemo {
    private static final String fileName = "Java-XML/src/main/resources/Launcher.xml";
    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document launcherXml = documentBuilder.parse(fileName);
        Element documentElementRoot = launcherXml.getDocumentElement();
        // 开始转换并输出内容
        System.out.println(convert(documentElementRoot, 0));
    }

    private static StringBuilder convert(Node node, int level) {
        // 元素节点
        if (node.getNodeType() == Node.ELEMENT_NODE){
            return elementObject((Element) node, level);
        }
        // 字符节点
        else if (node instanceof CharacterData){
            return characterString((CharacterData) node, level);
        }
        // 其他节点
        else {
            return pad(new StringBuilder(), level).append(
                    jsonEscape(node.getClass().getName())
            );
        }
    }
    private static Map<Character, String> replacements = new HashMap<>();
    static {
        replacements.put('\b', "\\b");
        replacements.put('\f', "\\f");
        replacements.put('\n', "\\n");
        replacements.put('\r', "\\r");
        replacements.put('\t', "\\t");
        replacements.put('"', "\\\"");
        replacements.put('\\', "\\\\");
    }

    private static StringBuilder jsonEscape(String str) {
        StringBuilder result = new StringBuilder("\"");
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            String replacement = replacements.get(ch);
            if (replacement == null) result.append(ch);
            else result.append(replacement);
        }
        result.append("\"");
        return result;
    }

    // 添加缩进
    private static StringBuilder pad(StringBuilder stringBuilder, int level) {
        for (int i = 0; i < level; i++){
            stringBuilder.append("  ");
        }
        return stringBuilder;
    }

    // 如果是comment字段的处理
    private static StringBuilder characterString(CharacterData node, int level) {

        StringBuilder result = new StringBuilder();
        StringBuilder data = jsonEscape(node.getData());
        if (node instanceof Comment) data.insert(1,"Comment: ");
        pad(result, level).append(data);
        return result;

    }

    // 处理所有的节点
    private static StringBuilder elementObject(Element elem, int level) {
        StringBuilder result = new StringBuilder();
        pad(result, level).append("{\n");
        // 标签名使用name属性
        pad(result,level + 1).append("\"name\": ");
        result.append(jsonEscape(elem.getTagName()));
        NamedNodeMap attrs = elem.getAttributes();
        // 对应属性
        if (attrs.getLength() > 0){
            pad(result.append(",\n"),level + 1).append("\"attributes\": ");
            result.append(attributeObject(attrs));
        }
        // 子节点！
        NodeList children = elem.getChildNodes();
        if (children.getLength() > 0){
            pad(result.append(",\n"),level + 1).append("\"children\": [\n");
            for (int i=0; i<children.getLength(); i++){
                if(i > 0) result.append(",\n");
                result.append(convert(children.item(i), level + 2));
            }
            result.append("\n");
            pad(result, level + 1).append("]\n");
        }
        pad(result, level).append("}");
        return result;
    }

    // 解析属性
    private static StringBuilder attributeObject(NamedNodeMap attrs) {
        StringBuilder result = new StringBuilder("{");
        for (int i=0;i<attrs.getLength(); i++){
            if (i >0) result.append(", ");
            result.append(jsonEscape(attrs.item(i).getNodeName()));
            result.append(":");
            result.append(jsonEscape(attrs.item(i).getNodeValue()));
        }
        result.append("}");
        return result;
    }

}
