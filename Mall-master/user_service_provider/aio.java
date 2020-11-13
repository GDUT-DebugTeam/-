import java.io.File;
import java.io.FileOutputStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
 
public class DOM4JParser {
     
    //读取第二个<name><name>
    @Test
    public void read() throws Exception{
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("src/student.xml"));
         
        //得到根节点
        Element root = document.getRootElement();
        //得到第二个<student><student>节点
        Element student = (Element)root.elements("student").get(1);
        //获取<name><name>中间的值
        String value = student.element("name").getText();
        System.out.println(value);//Bob
        //获取<name sex="xxx"><name>中间的sex值
        String sexValue = student.element("name").attributeValue("sex");
        System.out.println(sexValue);//xxx
    }
     
     
    //增加节点
    @Test
    public void add() throws Exception{
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("src/student.xml"));
         
        Element student = document.getRootElement().element("student");
        student.addElement("schoolName").setText("湖北");
         
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
         
        XMLWriter writer = new XMLWriter(new FileOutputStream("src/student.xml"),format);
        writer.write(document);
        writer.close();
         
    }
     
 
    //删除节点
    @Test
    public void delete() throws Exception{
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("src/student.xml"));
         
        Element student = (Element)document.getRootElement().elements("student").get(1);
        student.element("schoolName").setText("湖南");
         
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
         
        XMLWriter writer = new XMLWriter(new FileOutputStream("src/student.xml"),format);
        writer.write(document);
        writer.close();
         
    }
     
    //修改节点
    @Test
    public void update() throws Exception{
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File("src/student.xml"));
         
        Element student = document.getRootElement().element("student");
        Element schoolName = student.element("schoolName");
        schoolName.getParent().remove(schoolName);
         
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("utf-8");
         
        XMLWriter writer = new XMLWriter(new FileOutputStream("src/student.xml"),format);
        writer.write(document);
        writer.close();
         
    }
 
}
