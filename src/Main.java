import crearXML.Usuario;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    /**
     *
     * @param args the command line arguments
     */
    public static void main(String[] args){
        String nomArchivo = "Clientes";

        List<Usuario> listaUsuarios = new ArrayList<Usuario>();

        listaUsuarios.add(new Usuario("05530797-8","Andrea","Benítez", "1234567890123","GOLD",77951321));

        try{
            crearXML(nomArchivo, listaUsuarios);
            leerXML();
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

                Element itemNode = document.createElement("Cliente");

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

    //Lee en consola el archivo
    public static void leerXML(){
        try{
            File archivo = new File("Clientes.xml");

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
