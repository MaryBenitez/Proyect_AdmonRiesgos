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
            in = new BufferedReader(new FileReader("Cliente.txt")); //archivo.txt ya creado
            out = new StreamResult("Cliente.xml"); //archivo.xml a converitr
            openXml();//---> Funcion que abre xml (Estructura)
            String str;
            //While que lee cada linea del archivo.txt
            while ((str = in.readLine()) != null) {
                proceso(str);
            }
            in.close();
            closeXml();//---> Funcion que cierra xml (Estructura)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Abre el archivo XML (Estructura) para empezar a crearlo
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
        th.startElement("", "", "Clientes", atts);
    }

    //FUNCION de Proceso para crear el archivo.xml a traves de la informacion del archivo.txt
    public static void proceso(String s)  throws SAXException {

        String [] elements = s.split("\\;");
        atts.clear();
        th.startElement("","","cliente",atts);

        th.startElement("","","documento",atts);
        th.characters(elements[0].toCharArray(),0,elements[0].length());
        th.endElement("","","documento");

        th.startElement("","","primer-nombre",atts);
        th.characters(elements[1].toCharArray(),0,elements[1].length());
        th.endElement("","","primer-nombre");

        th.startElement("","","apellido",atts);
        th.characters(elements[2].toCharArray(),0,elements[2].length());
        th.endElement("","","apellido");

        th.startElement("","","credit-card",atts);
        th.characters(elements[3].toCharArray(),0,elements[3].length());
        th.endElement("","","credit-card");

        th.startElement("","","tipo",atts);
        th.characters(elements[4].toCharArray(),0,elements[4].length());
        th.endElement("","","tipo");

        th.startElement("","","telefono",atts);
        th.characters(elements[5].toCharArray(),0,elements[5].length());
        th.endElement("","","telefono");

        th.endElement("","","cliente");

    }

    //Cierra el archivo.xml (Estructura)
    public static void closeXml() throws SAXException {
        //th.endElement("", "", "cliente");
        th.endElement("", "", "Clientes");
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
