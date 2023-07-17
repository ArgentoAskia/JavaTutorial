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

/**
 * 这个Demo演示了基于DOM的XML中各种类型节点的基本CRUD！
 */
public class MybatisConfigXMLParseDemo {

    /**
     * 查询各类节点操作和解析XML
     *
     * - 一个Document对象就代表一个XML文件，DOM方式的解析会把XML文件解析成一个树（Tree）的结构
     * - 一般情况下，一个复杂的XML至少包含下面的部分：
     * 1.DocumentType（文档声明，如HTML中的<!DOCTYPE html>）
     * 2.EntityReference（实体引用，如&amp;、&lt;等）、
     * 3.Element（各类标签节点元素，如<select>、<sql>这些）、
     * 4.Attributes（标签节点上的属性，如id、lang、databaseId等等）、
     * 5.ProcessingInstruction（处理指令，以<? ?>开头和结尾，如：<?xml version="1.0" encoding="UTF-8" ?>）、
     * 6.Comment（注释）、
     * 7.Text（标签体内的内容）、
     * 8.CDATA（不会被解析处理的原始数据）、
     * 9.Entity（表示一个实体，对应<!ENTITY ...>）[一般XML很少写]
     * 10.Notation（表示一个注释声明，如<！NOTATION ...>。）[一般XML很少写]
     */
    private static void parse(Document document){
        System.out.println("==================== 文档解析 ====================");
        // 1.首先文档有两个预处理指令，可惜不像DocType一样，document没有现成的获取ProcessingInstruction的API
        // 而且要注意，如果是<?xml version="1.0" encoding="UTF-8"?>,将不会被解析成ProcessingInstruction,
        // 并且<?xml version="1.0" encoding="UTF-8"?>不作为文档的第一个元素
        // 你可以使用getXmlVersion()、getXmlEncoding()、getXmlStandalone()来获取这一特殊的ProcessingInstruction
        String xmlVersion = document.getXmlVersion();
        String xmlEncoding = document.getXmlEncoding();
        boolean xmlStandalone = document.getXmlStandalone();
        System.out.println("1.解析<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        System.out.println("XML版本：" + xmlVersion);
        System.out.println("XML编码：" + xmlEncoding);
        System.out.println("xmlStandalone：" + xmlStandalone);
        System.out.println();

        // 文档中的第二个ProcessingInstruction将会作为文档的第一个元素，我们可以通过调用getFirstChild()的方式来获取第一个元素
        // 那既然有getFirstChild()那就肯定有getLastChild()啦
        System.out.println("2.解析<?xml-stylesheet type=\"text/css\" href=\"css文件名.css\"?>");
        Node firstChild = document.getFirstChild();
        // 可以通过instanceof来判别节点类型，
        // 当然也可以通过使用通用的getNodeType() + Node常量的形式来判定类型，不过不建议这样做！
        if (firstChild.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE){
//        if (firstChild instanceof ProcessingInstruction){
            ProcessingInstruction processingInstruction = (ProcessingInstruction) firstChild;
            // 可以通过getTarget()方法获取指令头，也就是xml-stylesheet
            String target = processingInstruction.getTarget();
            // 可以通过getData()方法获取指令数据，也就是type="text/css" href="css文件名.css"
            String data = processingInstruction.getData();
            System.out.println("指令头：" + target);
            System.out.println("指令数据：" + data);
        }
        System.out.println();


        // 2.之后是两行注释,要想获取注释的话可以通过获取firstChild的兄弟节点的方式获取，即
        System.out.println("3.解析两个注释：");
        Node secondNode = firstChild.getNextSibling();
        // 获取前一个节点
//        Node previousSibling = firstChild.getPreviousSibling();
        if (secondNode instanceof Comment){
            Comment comment = (Comment) secondNode;
            String data = comment.getData();
            System.out.println("注释：" + data);
        }
        Node thirdNode = secondNode.getNextSibling();
        if (thirdNode instanceof Comment){
            Comment comment = (Comment) thirdNode;
            String data = comment.getData();
            System.out.println("注释：" + data);
        }
        System.out.println();


        // 3.之后是文档声明，DOCTYPE，Document有方便的方法获取DocType：getDoctype()获取DocumentType文档声明对象
        System.out.println("4.解析DocType：");
        DocumentType doctype = document.getDoctype();
        // 获取DocumentType后跟着的字符,也就是mapper
        String name = doctype.getName();
        // 获取Public后内容
        String publicId = doctype.getPublicId();
        // 获取System的Dtd内容
        String systemId = doctype.getSystemId();
        // 获取所有定义的实体（Node.ENTITY_NODE）
        NamedNodeMap entities = doctype.getEntities();
        // 获取所有定义的注释（Node.NOTATION_NODE）
        NamedNodeMap notations = doctype.getNotations();
        System.out.println("DOCTYPE：" + name);
        System.out.println("PUBLIC：" + publicId);
        System.out.println("SYSTEM：" + systemId);
        // 通过getNodeName()获取Entity的名称
        // 通过getSystemId()获取Entity|Notation的SYSTEM后面的值
        // 通过getPublicId()获取Entity|Notation的PUBLIC后面的值
        System.out.print("定义的实体：[");
        for (int i = 0; i < entities.getLength(); i++) {
            Node item = entities.item(i);
            if (item.getNodeType() == Node.ENTITY_NODE){
                Entity entity = (Entity) item;
                String entityName = entity.getNodeName();
                System.out.print(entityName);
            }
            if (i < entities.getLength() - 1){
                System.out.print(",");
            }
        }
        System.out.println("]");
        System.out.print("定义的Notation：[");
        for (int i = 0; i < notations.getLength(); i++) {
            Node item = notations.item(i);
            if (item.getNodeType() == Node.NOTATION_NODE){
                Notation notation = (Notation) item;
                String notationName = notation.getNodeName();
                String notationSystemId = notation.getSystemId();
                String notationPublicId = notation.getPublicId();
                System.out.print(notationName + ",SystemId:" + notationSystemId + ",publicId:" + notationPublicId);
            }
            if (i < entities.getLength() - 1){
                System.out.print(" | ");
            }
        }
        System.out.println("]");
        System.out.println();
        // 关于NamedNodeMap，是一个名字节点Map，Map结构，一般用来存储key=”value“的键值形式,如Attr节点
        // - getLength()：获取节点个数！
        // - item(int index):按照下标顺序获取节点！
        // - getNamedItem(String keyName):根据Key名字获取一个键值属性节点！
        // - removeNamedItem(String keyName):根据key名字移除一个键值属性节点！
        // - setNamedItem(Node arg):添加或者修改一个键值属性节点！

        // 对应的，也有NodeList结构！
        // NodeList是装Node的List,装载父节点的所有子节点，操作如下：
        // - getLength()：获取节点的数量
        // - item(int index):按照下标顺序获取节点！


        // 4.跳过后面的这两行注释，我们接下来解析Mapper根标签
        // 根标签document对象也有api：getDocumentElement()
        System.out.println("5.解析根标签：");
        Element root = document.getDocumentElement();
        // 使用getTagName()获取标签名
        String tagName = root.getTagName();
        System.out.println("标签名：" + tagName);

        // 使用getElementsByTagName("sql")获取标签的特定的子标签
        NodeList sqlLabels = root.getElementsByTagName("sql");
        System.out.print("输出SQL标签：[");
        for (int i = 0; i < sqlLabels.getLength(); i++) {
            Element item = (Element) sqlLabels.item(i);
            System.out.print(item.getTagName());
            if (i < sqlLabels.getLength() - 1){
                System.out.print(", ");
            }
        }
        System.out.println("]");

        // 使用getChildNodes()获取所有子标签
        NodeList childNodes = root.getChildNodes();
        // 为什么是13个而不是三个呢？因为DOM的解析会把一些空白位置也解析上
        // 如sql标签前面的换行符、空白符等等，这些会被解析成Text节点，所以我们需要过滤掉这些节点！
        System.out.println("输出所有子标签：" + childNodes.getLength() +"[");
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item1 = childNodes.item(i);
            if (item1 instanceof Element){
                Element item = (Element) item1;
                System.out.println("    第" + i + "个节点类型是：" + item.getNodeType() + ", 标签名是：" + item.getTagName());
            }else{
                System.out.println("    第" + i + "个节点类型是：" + item1.getNodeType());
            }
        }
        System.out.println("]");

        // 获取该元素属于的Document对象
        Document ownerDocument = root.getOwnerDocument();
        System.out.println("获取节点的所属的文档对象：" + ownerDocument);

        // 获取父节点
        Node parentNode = root.getParentNode();
        System.out.println("获取当前节点的父节点：" + parentNode);

        // 以文本方式获取节点的内容，对于mapper标签来说，标签体内的所有内容都属于，除非是CDATA
        // 比如这里将会获取第二个SQL标签的写法和Select标签以及他们的内容体
        String textContent = root.getTextContent();
        System.out.println("标签体的内容：" + textContent);

        // 使用getAttribute()、getAttributeNode()、getAttributes()获取标签属性
        String namespace = root.getAttribute("namespace");
        Attr namespaceAttr = root.getAttributeNode("namespace");
        NamedNodeMap attributes = root.getAttributes();
        System.out.println("属性值：namespace=" + "\"" +  namespace + "\"");
        // 如果获取的是Attr对象，则可以：使用getName()获取Attr的key、getValue()获取Attr的Value
        String key = namespaceAttr.getName();
        String value = namespaceAttr.getValue();
        System.out.println("Attr节点方式获取key和Value：" + key + "=" + "\"" + value + "\"");
        System.out.println();


        System.out.println("6.解析子标签：");
        // 获取所有Sql子节点
        NodeList sql = root.getElementsByTagName("sql");
        //  获取Select节点,因为只有一个节点，所以可以直接获取
        Element select = (Element)root.getElementsByTagName("select").item(0);
        Element sql0 = (Element)sql.item(0);
        Element sql1 = (Element)sql.item(1);
        // 第一个SQL标签获取属性
        String id = sql0.getAttribute("id");
        String lang = sql0.getAttribute("lang");
        // 第一个SQL标签获取标签体内容(实际上标签体内容被包装为一个Text节点,而且只有一个节点)
        Text sql0Text = (Text)sql0.getFirstChild();
        short nodeType = sql0Text.getNodeType();
        // 判断该节点是不是空白字符Text，一般用于过滤
        boolean elementContentWhitespace = sql0Text.isElementContentWhitespace();
        // 获取Text节点（标签体）数据内容
        String wholeText = sql0Text.getWholeText();
        System.out.println("第一个SQL标签：属性id：" + id + ", 属性lang：" + lang + ", 标签体是否是空白字符：" + elementContentWhitespace +
                ", 标签体内容（去掉回车符）：" + wholeText.trim() + ", 标签体内容（原始）：" + wholeText + ", 节点类型：" + nodeType);

        // 第二个SQL标签获取属性
        String id2 = sql1.getAttribute("id");
        String databaseId = sql1.getAttribute("databaseId");
        // 第一个SQL标签获取标签体内容(实际上标签体内容被包装为一个Text节点,而且只有一个节点)
        Text sql1Text = (Text)sql1.getFirstChild();
        short nodeType2 = sql1Text.getNodeType();
        // 判断该节点是不是空白字符Text，一般用于过滤
        boolean elementContentWhitespace2 = sql1Text.isElementContentWhitespace();
        // 获取Text节点（标签体）数据内容
        String wholeText2 = sql1Text.getWholeText();
        // 获取标签父节点名称
        String parentNodeName = ((Element) sql1.getParentNode()).getTagName();
        System.out.println("第二个SQL标签：属性id：" + id2 + ", 属性databaseId：" + databaseId + ", 标签体是否是空白字符：" + elementContentWhitespace2 +
                ", 标签体内容（去掉回车符）：" + wholeText2.trim() + ", 标签体内容（原始）：" + wholeText2 + ", 节点类型：" + nodeType2 + ", 父节点名称：" + parentNodeName);
    }

    /**
     * 添加、删除、修改操作！
     */
    private static void build(Document newDocument, Document document){

    }
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

        // 5.通过documentBuilder.newDocument()创建一个空Document对象
        Document newDocument = documentBuilder.newDocument();



        // toString()将会打印出NodeName和NodeValue

        parse(mybatisConfigDocument);



        // 2.Document对象的getChildNodes()获取当前文档的第一层的所有节点
        // 3.Document对象的getDocumentElement()来获取当前文档的根元素！
//        NodeList childNodes1 = mybatisConfigDocument.getChildNodes();
//        System.out.println("mybatis-config.xml第一层共有" + childNodes1.getLength() + "个节点");
//        for (int i = 0; i < childNodes1.getLength(); i++) {
//            Node item = childNodes1.item(i);
//            if (item instanceof Comment){
//                Comment comment = (Comment) item;
//                String nodeValue = comment.getNodeValue();
//                System.out.println("这是一段注释，注释的内容是：" + nodeValue);
//            }
//            if (item instanceof Attr){
//                Attr attr = (Attr) item;
//                String name = attr.getName();
//                String value = attr.getValue();
//                String tagName = attr.getOwnerElement().getTagName();
//                System.out.println("这是一个属性，属性名是" + name + "，属性值是：" + value + "，标签名：" + tagName);
//            }
//            if (item instanceof Text){
//                Text text = (Text) item;
//                String data = text.getData();
//                String wholeText = text.getWholeText();
//                System.out.println("这是一个标签文本节点：" + data);
//                System.out.println("这是一个标签文本节点：" + wholeText);
//            }
//            if (item instanceof Element){
//                Element element = (Element) item;
//                String tagName = element.getTagName();
//                System.out.println("这是一个标签，标签名是" + tagName);
//            }
//            if (item instanceof DocumentType){
//                DocumentType documentType = (DocumentType) item;
//                String name = documentType.getName();
//                String systemId = documentType.getSystemId();
//                String publicId = documentType.getPublicId();
//                System.out.println("这是一个文档声明，声明内容为：" + name + ",系统ID：" + systemId + ",公共ID：" + publicId);
//            }
//        }

        // 注意getElementById()获取的并不是id属性，需要在DocType中定义ID类型
//        Element insert = mybatisConfigDocument.getElementById("insert");
//        System.out.println(insert);

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

//        DOMConfiguration domConfig = mybatisConfigDocument.getDomConfig();
//        DOMStringList parameterNames = domConfig.getParameterNames();
//        int length = parameterNames.getLength();
//        String item = parameterNames.item(0);
//        System.out.println(item);
//        System.out.println(length);

        /*




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
