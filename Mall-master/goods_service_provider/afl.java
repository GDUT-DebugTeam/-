import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
 
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
 
 
public class SaxParser{
     
    public static void main(String[] args) throws Exception {
        //1.创建解析工厂
        SAXParserFactory spFactory = SAXParserFactory.newInstance();
        //2.得到解析器
        SAXParser sParser = spFactory.newSAXParser();
        //3.得到读取器
        XMLReader xmlReader = sParser.getXMLReader();
         
        //4.设置内容处理器
        xmlReader.setContentHandler(new TagDefaultHandler());
         
        //5.读取 XML 文档内容
        xmlReader.parse("src/student.xml");
    }
}
 
//第一种方法：继承接口ContentHandler 得到 XML 文档所有内容
class ListHandler implements ContentHandler{
 
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes atts) throws SAXException {
        System.out.println("<"+qName+">");
    }
 
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        System.out.println(new String(ch,start,length));
    }
     
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        System.out.println("</"+qName+">");
    }
 
    @Override
    public void setDocumentLocator(Locator locator) {
    }
 
    @Override
    public void startDocument() throws SAXException {
    }
 
    @Override
    public void endDocument() throws SAXException {
    }
 
    @Override
    public void startPrefixMapping(String prefix, String uri)
            throws SAXException {
    }
 
    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
    }
 
    @Override
    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
    }
 
    @Override
    public void processingInstruction(String target, String data)
            throws SAXException {
    }
 
    @Override
    public void skippedEntity(String name) throws SAXException {
    }
     
}
 
 
//使用继承类 DefaultHandler 更好
class TagDefaultHandler extends DefaultHandler{
    //当前解析的是什么标签
    private String currentTag;
    //想获得第几个标签的值
    private int tagNumber=0;
    //当前解析的是第几个标签
    private int currentNumber=0;
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        currentTag = qName;
        //当前解析的name 标签是第几个
        if("name".equals(currentTag)){
            currentNumber++;
            System.out.println(currentNumber);
        }
    }
     
    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        //打印所有name标签的值
        if("name".equals(currentTag)){
            System.out.println(new String(ch,start,length));
        }
        //想获得 第二个name标签的值
        tagNumber = 2;
        if("name".equals(currentTag)&&currentNumber==tagNumber){
            System.out.println(new String(ch,start,length));
        }      
    }
     
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        currentTag = null;
    }
}
