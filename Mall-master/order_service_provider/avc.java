
import java.io.FileOutputStream;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
 
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
 
public class DomParser{
     
    /**
     * 解析器读入整个文档，然后构建一个驻留内存的树结构，
     * 该方法返回 Document 对象，然后我们可以通过 这个对象来操作文档
     */
    public Document getDocument(String fileName) throws Exception{
        //1.创建解析工厂
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        //2.得到解析器
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        //3.得到文档对象
        Document document = dBuilder.parse(fileName);
         
        return document;
    }
     
    //读取xml文档中的数据
    public void read(String fileName) throws Exception{
        //获取 Document 对象
        Document document = new DomParser().getDocument(fileName);
         
        //获取<name></name>的节点
        NodeList nameNode = document.getElementsByTagName("name");
        //获取<name sex="xxx"></name>节点的sex属性
        Element element = (Element) document.getElementsByTagName("name").item(0);
        System.out.println(element.getAttribute("sex"));//xxx
         
        for(int i = 0 ; i < nameNode.getLength() ;i++){
            System.out.println(nameNode.item(i).getTextContent());
        }
        /**结果为
         * Tom
         * Bob
         * Marry
         */
         
        //获取文档的根元素对象
        Element rootElementName = document.getDocumentElement();
        System.out.println(rootElementName.getNodeName()); //students
         
        //得到根节点
        Node root = document.getElementsByTagName(rootElementName.getNodeName()).item(0);
        list(root);
         
    }
     
    //打印所有标签
    private void list(Node root) {
        if(root instanceof Element){
            System.out.println(root.getNodeName());
        }
        NodeList list = root.getChildNodes();
        for(int i = 0 ; i < list.getLength() ; i++){
            Node child = list.item(i);
            list(child);
        }
    }
     
    //向 xml 文件中增加节点和属性
    public void add(String fileName) throws Exception{
        //获取 Document 对象
        Document document = new DomParser().getDocument(fileName);
         
        //创建节点
        Element sex = document.createElement("sex");
        sex.setTextContent("男");
         
        //把创建的节点添加到第一个<student></student>标签上
        Element student = (Element) document.getElementsByTagName("student").item(0);
        student.appendChild(sex);
         
        //在<name></name>中增加属性 <name address="xxx"></name>
        Element name = (Element) document.getElementsByTagName("name").item(0);
        name.setAttribute("address", "xxx");
         
        //把更新后的内存写入xml文档中
        TransformerFactory tfFactory = TransformerFactory.newInstance();
        Transformer tFormer = tfFactory.newTransformer();
        tFormer.transform(new DOMSource(document),
                new StreamResult(new FileOutputStream("src/student.xml")));
    }
     
    //向 xml 文件中删除节点和属性
    public void delete(String fileName) throws Exception{
        //获取 Document 对象
        Document document = new DomParser().getDocument(fileName);
         
        //得到要删除的第一个<name></name>节点
        Element name = (Element) document.getElementsByTagName("name").item(0);
        //得到要删除的第一个<name></name>节点的父节点
        //Element student = (Element) document.getElementsByTagName("student").item(0);
        //student.removeChild(name);
        //上面两步可以简写为
        name.getParentNode().removeChild(name);
         
        //在<name></name>中删除属性 <name address="xxx"></name>
        name.removeAttribute("address");
         
        //把更新后的内存写入xml文档中
        TransformerFactory tfFactory = TransformerFactory.newInstance();
        Transformer tFormer = tfFactory.newTransformer();
        tFormer.transform(new DOMSource(document),
                new StreamResult(new FileOutputStream("src/student.xml")));
    }
 
     
    //向 xml 文件中更新节点和属性
    public void update(String fileName) throws Exception{
        //获取 Document 对象
        Document document = new DomParser().getDocument(fileName);
         
        //得到要删除的第一个<name></name>节点
        Element name = (Element) document.getElementsByTagName("name").item(0);
        //在<name></name>中更新属性 <name address="xxx"></name>为<name address="yyy"></name>
        name.setAttribute("address", "yyy");
        //更新name节点的文字为VAE,即<name>vae</name>
        name.setTextContent("vae");
         
        //把更新后的内存写入xml文档中
        TransformerFactory tfFactory = TransformerFactory.newInstance();
        Transformer tFormer = tfFactory.newTransformer();
        tFormer.transform(new DOMSource(document),
                new StreamResult(new FileOutputStream("src/student.xml")));
    }
}
