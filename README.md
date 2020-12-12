# Proyecto de Análisis de Riesgos Informáticos

﻿*** SW MARS+ ***
SW MARS+ es un software que tiene como principal proposito facilitar la conversión de archivos txt, xml y json, haciendo uso de delimitadores para el formato de dichos archivos y de métodos de cifrado para datos sensibles. 

SW MARS+ fue desarrollado con fines académicos por un grupo de estudiantes de la carrera de Ingeniería Informática de la UCA, como proyecto final de la materia Análisis de Riesgos Informáticos.

Para acceder al ejecutable de SW MARS+, se debe seguir la siguiente ruta:
MARS->MARS->MARS.exe

*** Instrucciones: ***
Para que que MARS+ funcione de la mejor manera, deberá seguir estos pasos:
1. Click en botón "Seleccionar Archivo"
2. En la ventana emergente, buscará la locación del archivo que desea convertir (txt, xml o json), y lo seleccionará.
3. Una vez seleccionado el archivo, podrá visualizar su contenido en pantalla.
4. Click en botón "Convertir este archivo"
5. Dependiendo del tipo de archivo se le habilitarán:
    *Los espacios donde escribir la llave para cifrar y que delimitador leer y/o usar
      -La llave para cifrar es necesaria para la conversion de XML a TXT y viceversa. **CON LA MISMA CLAVE QUE SE CIFRÓ, DEBE DESCIFRARSE
      -El delimitador SIEMPRE es necesario, ya sea para leer el archivo TXT o para colocarlo en la conversion de XML/JSON a TXT
***NOTA: Estos campos deben llenarse antes de clickear cualquier botón de conversion
    *Los botones correspondientes a la conversión:
      Si el archivo es .txt se habilitarán los botones:
      -> Convertir a XML
      -> Convertir a JSON
      Si el archivo es .xml se habilitará el botón:
      -> Convertir XML a TXT
      Si el archivo es .json se habilitará el botón:
      -> Convertir JSON a TXT
6. Al clickear cualquier opción de conversión, en la ventana emergente deberá buscar la ubicación donde desee que se guarde el resultante.
7. Con el Botón "Mostrar Archivo" podrá verificar que su archivo ha sido creado correctamente, al darle click emergerá una ventana donde buscará la ubicación donde guardó el archivo, y al seleccionarlo, su contenido se motrará en pantalla.
8. Botón "Cancelar" si no desea convetir archivo seleccionado o si desea empezar de nuevo una nueva conversión.

La presente versión es la última hasta el momento, sin embargo no se descarta la posibilidad de que el software sea actualizado y mejorado en un futuro.

////***JWT es creado y descifrado a nivel de consola***////
Link del repositorio con interfaz gráfica:
https://github.com/MarleneBarahona/ProyectoARI.git
