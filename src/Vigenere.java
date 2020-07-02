public class Vigenere {

    char[] mensaje;
    char[] clave;
    char[] resultado; //resultado cifrado
    char matriz[][];

    public Vigenere(String msg,String clave)
    {

        this.mensaje = msg.toCharArray();
        char[] claveTemp = clave.toCharArray();
        this.clave = new char[mensaje.length];
        int cont =0;

        //For mete la clave multiples veces en 1 arreglo
        for(int i=0;i<mensaje.length;i++){
            this.clave[i]=claveTemp[cont];
            cont++;
            if(cont==claveTemp.length)
                cont=0;
        }
        //la clave ya se guardo en un arreglo de igual tamaño que del mensaje

        this.matriz = generarMatrizABC();//Generamos matriz del abecedarioç
        cifrar(); //ciframos el texto
        System.out.println("-----------------------------");
        decifrar();
    }

    //Genera cifrado
    public void cifrar(){
        char[] cifrado = new char[mensaje.length];
        int i;
        int j;
        for(int cont=0;cont<mensaje.length;cont++){
            //Primero calculamos el indice de cada matriz "i" y "j"
            //el indice "i" correspondera al arreglo del mensaje
            //el indice "j" correspondera al arreglo de la clave
            //luego ejecutaremos el calculo para cifrar utilizando "i" y "j" como cordenadas de la matriz

            i=(int)this.mensaje[cont]-48; //restamos 48 para pasar de codigo ASCII a un numero entero
            j=(int)this.clave[cont]-48;
            cifrado[cont]=this.matriz[i][j];

        }

        this.resultado = cifrado;

        System.out.println();
        System.out.println("MENSAJE:");
        System.out.println(this.mensaje);
        System.out.println("CLAVE:");
        System.out.println(this.clave);
        System.out.println("CIFRADO VIGENERE:");
        System.out.println(cifrado);
    }

    public String getMensajeCifrado(){
        String result="";
        for(int i=0;i<resultado.length;++i)
            result=result+this.resultado[i];
        //System.out.println(result);
        return result;
    }

    public void OtorgarValor(){
        char[] abc = generarAbecedario();
        int pos = 0;
        int temp = pos;
        int[] arr = new int[temp];
        for (int i=0; i<abc.length; i++){
            abc[i] = (char) pos;
            //System.out.println(pos);
            pos = temp;
            temp = arr[pos];
            System.out.println(temp+i);
            pos++;
        }
    }

    public void decifrar(){

        getMensajeCifrado();
        OtorgarValor();
        //int arrV = OtorgarValor();
        //System.out.println(arrV);
        char[] mjsC = getMensajeCifrado().toCharArray();
        for (int i=0; i<mjsC.length; i++){

        }
        //System.out.println(abc);

    }

    //Genera la Matriz de Vigenere
    private char[][] generarMatrizABC(){
        int contador;
        char abcTemp[] = this.generarAbecedario();
        char abc[] = new char[abcTemp.length*2];
        for(int c=0;c<75;c++){
            abc[c]=abcTemp[c];
            abc[c+75]=abcTemp[c];
        }
        char[][] matriz = new char[75][75];
        for(int i=0;i<75;i++){
            contador=0;
            for(int j=0;j<75;j++) {
                matriz[i][j]=abc[contador+i];
                contador++;
            }
        }
        return matriz;
    }

    //Genera los caracteres a utilizar
    private char[] generarAbecedario(){
        char[] abc = new char[75];

        for(int i= 48; i<= 122;i++){
            abc[i-48]=(char)i;
        }
        return abc;
    }

}
