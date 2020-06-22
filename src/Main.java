import Cliente.Usuario;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

    /**
     *
     * @param args the command line arguments
     */

    private static BufferedReader in; //Entrada
    private static StreamResult out; //Salida
    private static TransformerHandler th;
    private static AttributesImpl atts;

    public static void main(String[] args){
        String nomArchivo = "Clientes";

        //List<Usuario> listaUsuarios = new ArrayList<Usuario>();

       // listaUsuarios.add(new Usuario("05530797-8","Andrea","Benítez", "1234567890123","GOLD",77951321));
       // listaUsuarios.add(new Usuario("05530797-2","Andrea","Dominguez", "1234567890123","GOLD",77951321));

        try{
            //crearXML(nomArchivo, listaUsuarios);
            //leerXML();
            convertirTXTtoXML();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //Funcion para crear el archivo XML
    public static void crearXML(String nomArchivo, List<Usuario> listaUsuarios){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            Document document = implementation.createDocument(null, nomArchivo, null);
            document.setXmlVersion("1.0");

            //Nodo Raiz donde recorrera la lista de los usuarios
            Element raiz = document.getDocumentElement();
            for (int i=0; i< listaUsuarios.size(); i++){

                Element itemNode = document.createElement("cliente");

                //Obteniendo atributos del Usuario
                Element duiNode = document.createElement("documento");
                Text nodeDuiValue = document.createTextNode(""+listaUsuarios.get(i).getDocumento());
                duiNode.appendChild(nodeDuiValue);

                Element nombreNode = document.createElement("primer-nombre");
                Text nodeNombreValue = document.createTextNode(""+listaUsuarios.get(i).getNombre());
                nombreNode.appendChild(nodeNombreValue);

                Element apellidoNode = document.createElement("apellido");
                Text nodeApellidoValue = document.createTextNode(""+listaUsuarios.get(i).getApellido());
                apellidoNode.appendChild(nodeApellidoValue);

                Element nTarjetaNode = document.createElement("credit-card");
                Text nodeNTarjetaValue = document.createTextNode(""+listaUsuarios.get(i).getNtarjeta());
                nTarjetaNode.appendChild(nodeNTarjetaValue);

                Element tipoTarjetaNode = document.createElement("tipo");
                Text nodeTipoTarjetaValue = document.createTextNode(""+listaUsuarios.get(i).getTipoTarjeta());
                tipoTarjetaNode.appendChild(nodeTipoTarjetaValue);

                Element telefonoNode = document.createElement("telefono");
                Text nodeTelefonoValue = document.createTextNode(""+listaUsuarios.get(i).getTelefono());
                telefonoNode.appendChild(nodeTelefonoValue);

                //Coloca los atributos del usuario
                itemNode.appendChild(duiNode);
                itemNode.appendChild(nombreNode);
                itemNode.appendChild(apellidoNode);
                itemNode.appendChild(nTarjetaNode);
                itemNode.appendChild(tipoTarjetaNode);
                itemNode.appendChild(telefonoNode);

                raiz.appendChild(itemNode);
            }

            //Generar XML
            Source source = new DOMSource(document);

            //Donde se guardara
            Result result = new StreamResult(new java.io.File(nomArchivo+".xml"));
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);

        }catch (ParserConfigurationException | TransformerException e){
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    //Funcion para convetir TXT a XML
    public static void convertirTXTtoXML() {
        try {
            //.txt
            in = new BufferedReader(new FileReader("Cliente.txt"));
            //.xml
            out = new StreamResult("Cliente.xml");
            openXml();
            String str;
            /*while ((str = in.readLine()) != null) {
                proceso(str);
            }*/

            int counter = -1;
            int counter2 = 49;
            while ((str = in.readLine()) != null) {
                counter++;
                //counter2++;
                if(counter % 6 == 0) {
                    proceso1(str); // add DOCUMENT element
                    continue;
                }
                if(counter % 6 == 1) {
                    proceso2(str); // add NOMBRE element
                    continue;
                }
                if(counter % 6 == 2) {
                    proceso3(str); // add APELLIDO element
                    continue;
                }
                if(counter % 6 == 3) {
                    proceso4(str); // add NUMERO DE TRAJETA element
                    continue;
                }
                if(counter % 6 == 4) {
                    proceso5(str); // add TIPO element
                    continue;
                }
                if(counter % 6 == 5) {
                    proceso6(str); // add TELEFONO element
                    continue;
                }
            }

            in.close();
            closeXml();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Abre el archivo XML para empezar a crearlo
    public static void openXml() throws ParserConfigurationException, TransformerConfigurationException, SAXException {

        SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        th = tf.newTransformerHandler();

        // SALIDA XML
        Transformer serializer = th.getTransformer();
        serializer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        serializer.setOutputProperty(OutputKeys.INDENT,"yes");

        //Se empiezan a poner las etiquetas
        th.setResult(out);
        th.startDocument();
        atts = new AttributesImpl();
        th.startElement(null, null, "Clientes", atts);
        th.startElement("", "", "cliente", atts);
    }

    //Procesos para crear el archivo XML a traves de la informacion del .txt

    public static void proceso1(String s)  throws SAXException {

        String[] elements = s.split(" ");
        atts.clear();

        //Se inserta la informacion del .txt
        th.startElement("", "", "documento", atts);
        th.characters(elements[0].toCharArray(), 0, elements[0].length());
        th.endElement("", "", "documento");

    }

    public static void proceso2(String s)  throws SAXException {

        String[] elements = s.split(" ");
        atts.clear();

        //Se inserta la informacion del .txt
        th.startElement("", "", "primer-nombre", atts);
        th.characters(elements[0].toCharArray(), 0, elements[0].length());
        th.endElement("", "", "primer-nombre");
    }
    public static void proceso3(String s)  throws SAXException {

        String[] elements = s.split(" ");
        atts.clear();

        //Se inserta la informacion del .txt
        th.startElement("", "", "apellido", atts);
        th.characters(elements[0].toCharArray(), 0, elements[0].length());
        th.endElement("", "", "apellido");
    }
    public static void proceso4(String s)  throws SAXException {

        String[] elements = s.split(" ");
        atts.clear();

        //Se inserta la informacion del .txt
        th.startElement("", "", "credit-card", atts);
        th.characters(elements[0].toCharArray(), 0, elements[0].length());
        th.endElement("", "", "credit-card");
    }
    public static void proceso5(String s)  throws SAXException {

        String[] elements = s.split(" ");
        atts.clear();

        //Se inserta la informacion del .txt
        th.startElement("", "", "tipo", atts);
        th.characters(elements[0].toCharArray(), 0, elements[0].length());
        th.endElement("", "", "tipo");
    }
    public static void proceso6(String s)  throws SAXException {

        String[] elements = s.split(" ");
        atts.clear();

        //Se inserta la informacion del .txt
        th.startElement("", "", "telefono", atts);
        th.characters(elements[0].toCharArray(), 0, elements[0].length());
        th.endElement("", "", "telefono");
    }



    //Cierra el archivo XML
    public static void closeXml() throws SAXException {
        th.endElement("", "", "cliente");
        th.endElement(null, null, "Clientes");
        th.endDocument();
    }


    //Lee en consola el archivo
    public static void leerXML(){
        try{
            File archivo = new File("Prueba.txt");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            Document document = documentBuilder.parse(archivo);

            document.getDocumentElement().normalize();

            System.out.println("Elemento raiz: " + document.getDocumentElement().getNodeName());

            NodeList listaUsuarios = document.getElementsByTagName("Cliente");

            for (int i=0; i < listaUsuarios.getLength(); i++){
                Node nodo = listaUsuarios.item(i);

                System.out.println("Elemento: " + nodo.getNodeName());

                if(nodo.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) nodo;

                    System.out.println("documento: " + element.getElementsByTagName("documento").item(i).getTextContent());
                    System.out.println("primer-nombre: " + element.getElementsByTagName("primer-nombre").item(i).getTextContent());
                    System.out.println("apellido: " + element.getElementsByTagName("apellido").item(i).getTextContent());
                    System.out.println("credit-card: " + element.getElementsByTagName("credit-card").item(i).getTextContent());
                    System.out.println("tipo: " + element.getElementsByTagName("tipo").item(i).getTextContent());
                    System.out.println("telefono: " + element.getElementsByTagName("telefono").item(i).getTextContent());

                    System.out.println("");

                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
