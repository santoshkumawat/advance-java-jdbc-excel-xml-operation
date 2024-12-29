package file;

import dao.UserDao;
import models.Users;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Attribute;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FileOperations {

    public void writeDataToExcel(List<Users> users){
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Users");
        int rowNum = 0;
        for(Users user: users){
            sheet.createRow(rowNum).createCell(0).setCellValue(user.getId());
            sheet.getRow(rowNum).createCell(1).setCellValue(user.getName());
            sheet.getRow(rowNum).createCell(2).setCellValue(user.getAge());
            rowNum++;
        }
        try {
            FileOutputStream fos = new FileOutputStream("Users.xlsx");
            workbook.write(fos);
            System.out.println("Data inserted into excel file");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void readDataFromExcel(String filePath) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(filePath);
        XSSFSheet sheet = workbook.getSheet("Users");

        for(int i=0; i<= sheet.getLastRowNum(); i++) {
            int id = (int) sheet.getRow(i).getCell(0).getNumericCellValue();
            String name = sheet.getRow(i).getCell(1).getStringCellValue();
            int age = (int) sheet.getRow(i).getCell(2).getNumericCellValue();

//            System.out.println(id + " " + name + " " + age);
            Users user = new Users();
            user.setName(name);
            user.setAge(age);

            UserDao userDao = new UserDao();

            Users existingUser = userDao.fetchUser(id);
            if (existingUser == null) {
                userDao.insertData(user);
                System.out.println("User with id " + id + " inserted in the DB");
            } else {
                System.out.println("User with id " + id + " already exists");
            }
        }

    }

    public void writeDataToXML(List<Users> users){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element rootElement = document.createElement("Users");
            document.appendChild(rootElement);
            for(Users user:users){
                Element userElement = document.createElement("User");

                Element id = document.createElement("ID");
                id.appendChild(document.createTextNode(String.valueOf(user.getId())));
                userElement.appendChild(id);

                Element name = document.createElement("Name");
                name.appendChild(document.createTextNode(user.getName()));
                userElement.appendChild(name);

                Element age = document.createElement("Age");
                age.appendChild(document.createTextNode(String.valueOf(user.getAge())));
                userElement.appendChild(age);

                rootElement.appendChild(userElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("Users.xml"));

            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }


    }

    public void readDataFromXML(){
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse("Users.xml");

            NodeList nodeList = document.getElementsByTagName("User");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element userElement = (Element) nodeList.item(i);

                int id = Integer.parseInt(userElement.getElementsByTagName("ID").item(0).getTextContent());
                String name = userElement.getElementsByTagName("Name").item(0).getTextContent();
                int age = Integer.parseInt(userElement.getElementsByTagName("Age").item(0).getTextContent());

                System.out.println("id: " +id + " name: " + name + " age: " + age);
            }
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
