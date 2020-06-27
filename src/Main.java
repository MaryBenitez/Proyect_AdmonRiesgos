import Cliente.Cliente;
import com.google.gson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
     * @param args the command line arguments
     */

    private static BufferedReader in; //Entrada
    private static StreamResult out; //Salida
    private static TransformerHandler th;
    private static AttributesImpl atts;

    private static String id;
    private static String issuer;
    private static String subject;

    private static String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";


    public static void main(String[] args) {

        Cliente cliente = new Cliente();


        try {
            //crearXML(nomArchivo, listaUsuarios);
            convertirTXTtoXML();
            convertirTXTtoJson();
            convertirXMLtoTXT();
            convertirJSONtoTXT();

        } catch (Exception e) {
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
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");

        //Se empiezan a poner las etiquetas
        th.setResult(out);
        th.startDocument();
        atts = new AttributesImpl();
        th.startElement("", "", "Clientes", atts);
    }

    //FUNCION de Proceso para crear el archivo.xml a traves de la informacion del archivo.txt
    public static void proceso(String s) throws SAXException {

        String[] elements = s.split("\\;"); //--->Delimitador
        atts.clear();
        th.startElement("", "", "cliente", atts);

        th.startElement("", "", "documento", atts);
        th.characters(elements[1].toCharArray(), 0, elements[1].length());
        th.endElement("", "", "documento");

        th.startElement("", "", "primer-nombre", atts);
        th.characters(elements[2].toCharArray(), 0, elements[2].length());
        th.endElement("", "", "primer-nombre");

        th.startElement("", "", "apellido", atts);
        th.characters(elements[3].toCharArray(), 0, elements[3].length());
        th.endElement("", "", "apellido");

        th.startElement("", "", "credit-card", atts);
        th.characters(elements[4].toCharArray(), 0, elements[4].length());
        th.endElement("", "", "credit-card");

        th.startElement("", "", "tipo", atts);
        th.characters(elements[5].toCharArray(), 0, elements[5].length());
        th.endElement("", "", "tipo");

        th.startElement("", "", "telefono", atts);
        th.characters(elements[6].toCharArray(), 0, elements[6].length());
        th.endElement("", "", "telefono");

        th.endElement("", "", "cliente");

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
            String titulo = "id;documento;primer-nombre;apellido;credit-card;tipo;telefono";
            String line;//--> Para las linea que leera del txt
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
        try {
            //Se crea el fichero JSON en la ruta establecida
            writer = new FileWriter("Cliente.json");
            //System.out.println(gson.toJson(datasets)); //consola
            //Se crea el JSON y lo escribimos en el archivo.
            gson.toJson(datasets, writer);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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

    public static void convertirXMLtoTXT() throws IOException {

        try {
            //Archivo.txt que va a crear
            BufferedWriter writer = new BufferedWriter(new FileWriter("Cliente-convertido-XML.txt"));
            //Archivo que leerá
            File fXmlFile = new File("Cliente.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //Etiqueta va a leer para pasarla a texto
            NodeList nList = doc.getElementsByTagName("cliente");

            //Nodo Padre
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    //Creo un elemento que obtendra los hijos
                    Element eElement = (Element) node;
                    if (eElement.hasChildNodes()) {
                        NodeList nl = node.getChildNodes();
                        for (int j = 0; j < nl.getLength(); j++) {
                            Node nd = nl.item(j);
                            String name = nd.getTextContent();
                            //Compruebo que no este vacio y escribo en el archivo.txt
                            if (name != null && !name.trim().equals("")) {
                                writer.write(nd.getTextContent().trim());
                                //Para que el último dato no tenga el delimitador
                                if (j < nl.getLength() - 2) {
                                    writer.write(";");
                                }
                            }
                        }
                    }
                }
                writer.write("\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convertirJSONtoTXT() throws IOException, ParseException {

        //Archivo al que vamos a reescribir el JSON
        Writer writer = null;
        writer = new FileWriter("Cliente-convertido-JSON.txt");

        JSONParser parser = new JSONParser();
        JSONArray a = (JSONArray) parser.parse(new FileReader("Cliente.json"));
        try {
            for (Object o : a) {

                JSONObject jsonObject =  (JSONObject) o;

                String id = (String) jsonObject.get("id");
                //System.out.println(id);
                String doc = (String) jsonObject.get("documento");
                //System.out.println(doc);
                String nombre = (String) jsonObject.get("primer-nombre");
                //System.out.println(nombre);
                String apellido = (String) jsonObject.get("apellido");
                //System.out.println(apellido);
                String nTarjeta = (String) jsonObject.get("credit-card");
                //System.out.println(nTarjeta);
                String tipo = (String) jsonObject.get("tipo");
                //System.out.println(tipo);
                String telefono = (String) jsonObject.get("telefono");
                //System.out.println(telefono);
                if(jsonObject.get("id") == id) {
                    writer.write(id);
                    writer.write(";");
                    System.out.println("ESCRIBIO EL ID");
                }
                if(jsonObject.get("documento") == doc){
                    writer.write(doc);
                    writer.write(";");
                    System.out.println("ESCRIBIO EL DOCUMENTO");
                }
                if(jsonObject.get("primer-nombre") == nombre){
                    writer.write(nombre);
                    writer.write(";");
                    System.out.println("ESCRIBIO EL NOMBRE");
                }
                if(jsonObject.get("apellido") == apellido){
                    writer.write(apellido);
                    writer.write(";");
                    System.out.println("ESCRIBIO EL APELLIDO");
                }
                if(jsonObject.get("credit-card") == nTarjeta){
                    writer.write(nTarjeta);
                    writer.write(";");
                    System.out.println("ESCRIBIO EL NUMERO DE TARJETA");
                }
                if(jsonObject.get("tipo") == tipo){
                    writer.write(tipo);
                    writer.write(";");
                    System.out.println("ESCRIBIO EL TIPO");
                }
                if(jsonObject.get("telefono") == telefono){
                    writer.write(telefono);
                    writer.write("\n");
                    System.out.println("ESCRIBIO EL TELEFONO");
                }
            }
        } finally {
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

    public static void guardarJsonJWT (){

    }



}


