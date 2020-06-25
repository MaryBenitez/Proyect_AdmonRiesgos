import com.google.gson.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Arrays;
import java.util.List;

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


        try{
            //crearXML(nomArchivo, listaUsuarios);
            convertirTXTtoXML();
            convertirTXTtoJson();
            convertirXMLtoTXT();
            convertirJSONtoTXT();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //Funcion para convetir TXT a XML
    public static void convertirTXTtoXML() {
        String line;
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

        String [] elements = s.split("\\;"); //--->Delimitador
        atts.clear();
        th.startElement("","","cliente",atts);

        th.startElement("","","documento",atts);
        th.characters(elements[1].toCharArray(),0,elements[1].length());
        th.endElement("","","documento");

        th.startElement("","","primer-nombre",atts);
        th.characters(elements[2].toCharArray(),0,elements[2].length());
        th.endElement("","","primer-nombre");

        th.startElement("","","apellido",atts);
        th.characters(elements[3].toCharArray(),0,elements[3].length());
        th.endElement("","","apellido");

        th.startElement("","","credit-card",atts);
        th.characters(elements[4].toCharArray(),0,elements[4].length());
        th.endElement("","","credit-card");

        th.startElement("","","tipo",atts);
        th.characters(elements[5].toCharArray(),0,elements[5].length());
        th.endElement("","","tipo");

        th.startElement("","","telefono",atts);
        th.characters(elements[6].toCharArray(),0,elements[6].length());
        th.endElement("","","telefono");

        th.endElement("","","cliente");

    }

    //Cierra el archivo.xml (Estructura)
    public static void closeXml() throws SAXException {
        th.endElement("", "", "Clientes");
        th.endDocument();
    }

    //Funcion para convetir TXT a JSON
    public static void convertirTXTtoJson() throws IOException {

        //Crea una matriz donde se almacenaran los datos
        JsonArray datasets = new JsonArray();

        try (BufferedReader br = new BufferedReader(new FileReader("Cliente.txt"))) {
            //Titlos para el JSON
            String titulo = "id;documento;nombre;apellido;tarjeta;tipo;telefono";
            String line ;//--> Para las linea que leera del txt
            boolean flag = true;
            List<String> columns = null;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                if (flag) {
                    //process Titulos;
                    columns = Arrays.asList(titulo.split(";")); //---> delimitador
                    //Se crea el objeto JSON y lo almacena temporalmente
                    JsonObject obj = new JsonObject();
                    //Información del cliente (Linea por linea del archivo)
                    List<String> chunks = Arrays.asList(line.split(";")); //---> delimitador
                    for (int i = 0; i < columns.size(); i++) {
                        obj.addProperty(columns.get(i), chunks.get(i));
                    }
                    //Agrega los datos a la matriz
                    datasets.add(obj);
                } else {
                    flag = false;
                }
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("Archivo no encontrado");
        } catch (IOException io) {
            System.out.println("No se pudo leer el archivo");
        }

        //Aqui se le da el formato de JSON y se empieza a crear
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
        Writer writer = null;
        try{
            //Se crea el fichero JSON en la ruta establecida
            writer = new FileWriter("Cliente.json");
            //System.out.println(gson.toJson(datasets)); //consola
            //Se crea el JSON y lo escribimos en el archivo.
            gson.toJson(datasets, writer);

        }catch(IOException e){
            e.printStackTrace();
        } finally{
            // Cerramos el archivo
            try {
                //Verificamos que no este nulo
                if (null != writer) {
                    writer.flush();
                    writer.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void convertirXMLtoTXT() throws IOException{

        try {
            //Archivo.txt que va a crear
            BufferedWriter writer = new BufferedWriter(new FileWriter("Cliente-convertivo-xml.txt"));
            //Archivo que leerá
            File fXmlFile = new File("Cliente.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //Etiqueta va a leer para pasarla a texto
            NodeList nList = doc.getElementsByTagName("cliente");

            //Nodo Padre
            for (int i = 0; i < nList.getLength(); i++){
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    //Creo un elemento que obtendra los hijos
                    Element eElement = (Element) node;
                    if(eElement.hasChildNodes()) {
                        NodeList nl = node.getChildNodes();
                        for(int j=0; j<nl.getLength(); j++) {
                            Node nd = nl.item(j);
                            String name= nd.getTextContent();
                            //Compruebo que no este vacio y escribo en el archivo.txt
                            if (name != null && !name.trim().equals("")){
                                writer.write(nd.getTextContent().trim());
                                //Para que el último dato no tenga el delimitador
                                if(j < nl.getLength()-2){
                                    writer.write(";");
                                }
                            }
                        }
                    }
                }
                writer.write("\n");
            }
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convertirJSONtoTXT(){

    }

}


