package cn.argentoaskia.dom;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLOutput;

public class MybatisConfigXMLParseDemo {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerConfigurationException {
        // 1. 读入XML文件到流，File对象或者URL等都ok
        InputStream mybatisConfigXml = MybatisConfigXMLParseDemo.class.getResourceAsStream("/mybatis-config.xml");

        // 2. 通过DocumentBuilderFactory.newInstance()获取DocumentBuilderFactory对象
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        // 3. 通过documentBuilderFactory.newDocumentBuilder()来创建DocumentBuilder对象
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        // 4.通过documentBuilder.parse()来解析XML输入流、XML File对象或者XML文件URL等
        //   也可以使用newDocument()创建一个空的XML文档。
        Document mybatisConfigDocument = documentBuilder.parse(mybatisConfigXml);


        // 5.一个Document对象就代表一个XML文件，DOM方式的解析会把XML文件解析成一个树（Tree）的结构
        // 一般情况下，一个复杂的XML至少包含下面的部分：DocumentType、EntityReference、Element、Attributes、ProcessingInstruction
        // Comment、Text、CDATA、Entity等
        NodeList childNodes1 = mybatisConfigDocument.getChildNodes();
        System.out.println("mybatis-config.xml共有" + childNodes1.getLength() + "个节点");
        for (int i = 0; i < childNodes1.getLength(); i++) {
            Node item = childNodes1.item(i);
            if (item instanceof Comment){
                Comment comment = (Comment) item;
                String nodeValue = comment.getNodeValue();
                System.out.println("这是一段注释，注释的内容是：" + nodeValue);
            }
            if (item instanceof Attr){
                Attr attr = (Attr) item;
                String name = attr.getName();
                String value = attr.getValue();
                String tagName = attr.getOwnerElement().getTagName();
                System.out.println("这是一个属性，属性名是" + name + "，属性值是：" + value + "，标签名：" + tagName);
            }
            if (item instanceof Text){
                Text text = (Text) item;
                String data = text.getData();
                String wholeText = text.getWholeText();
                System.out.println("这是一个标签文本节点：" + data);
                System.out.println("这是一个标签文本节点：" + wholeText);
            }
            if (item instanceof Element){
                Element element = (Element) item;
                String tagName = element.getTagName();
                System.out.println("这是一个标签，标签名是" + tagName);
            }
            if (item instanceof DocumentType){
                DocumentType documentType = (DocumentType) item;
                String name = documentType.getName();
                String systemId = documentType.getSystemId();
                String publicId = documentType.getPublicId();
                System.out.println("这是一个文档声明，声明内容为：" + name + ",系统ID：" + systemId + ",公共ID：" + publicId);
            }
        }

        // 注意getElementById()获取的并不是id属性，需要在DocType中定义ID类型
        Element insert = mybatisConfigDocument.getElementById("insert");

        NodeList select = mybatisConfigDocument.getElementsByTagName("select");
        NodeList sql = mybatisConfigDocument.getElementsByTagName("sql");
        NodeList childNodes = mybatisConfigDocument.getChildNodes();
        Node firstChild = mybatisConfigDocument.getFirstChild();
        Node lastChild = mybatisConfigDocument.getLastChild();
        Node parentNode = mybatisConfigDocument.getParentNode();
        Node nextSibling = mybatisConfigDocument.getNextSibling();
        Node previousSibling = mybatisConfigDocument.getPreviousSibling();
        Element documentElement = mybatisConfigDocument.getDocumentElement();

        NodeList elementsByTagNameNS = mybatisConfigDocument.getElementsByTagNameNS("*", "*");


        /*
        // 5.1 获取DocumentType
        DocumentType doctype = mybatisConfigDocument.getDoctype();
        // 获取DocumentType后跟着的字符
        String name = doctype.getName();
        // 获取Public后内容
        String publicId = doctype.getPublicId();
        // 获取System的Dtd内容
        String systemId = doctype.getSystemId();
        System.out.println("DOCTYPE：" + name);
        System.out.println("PUBLIC：" + publicId);
        System.out.println("SYSTEM：" + systemId);

        // 获取所有标签体内的内容
        String textContent = mybatisConfigDocument.getTextContent();

        // Node通用获取
        String nodeName = mybatisConfigDocument.getNodeName();
        short nodeType = mybatisConfigDocument.getNodeType();
        String nodeValue = mybatisConfigDocument.getNodeValue();

        DOMConfiguration domConfig = mybatisConfigDocument.getDomConfig();
        DOMStringList parameterNames = domConfig.getParameterNames();
        int length = parameterNames.getLength();
        System.out.println(length);
        String inputEncoding = mybatisConfigDocument.getInputEncoding();
        System.out.println(inputEncoding);
        String xmlEncoding = mybatisConfigDocument.getXmlEncoding();
        System.out.println(xmlEncoding);
        String xmlVersion = mybatisConfigDocument.getXmlVersion();
        System.out.println(xmlVersion);

        Document ownerDocument = mybatisConfigDocument.getOwnerDocument();
        String prefix = mybatisConfigDocument.getPrefix();









        Object userData = mybatisConfigDocument.getUserData("123");



        // unknown
        String baseURI = mybatisConfigDocument.getBaseURI();
        String documentURI = mybatisConfigDocument.getDocumentURI();
        NamedNodeMap attributes = mybatisConfigDocument.getAttributes();
        boolean xmlStandalone = mybatisConfigDocument.getXmlStandalone();
        String localName = mybatisConfigDocument.getLocalName();
        String namespaceURI = mybatisConfigDocument.getNamespaceURI();

        // unknown how to use
        DOMImplementation implementation = mybatisConfigDocument.getImplementation();


         */
        mybatisConfigXml.close();
    }
}
