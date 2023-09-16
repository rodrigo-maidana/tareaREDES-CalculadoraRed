# tareaREDES-CalculadoraRed
Su tarea es implementar una calculadora de Red, utilizando un lenguaje de programación Java

Requerimientos:
1 - El programa debe poder recibir un IP/MASK como entrada
2 - Generar el NETWORK y BROADCAST para la entrada especificada.
3 - La máscara de red puede ser ingresada en formato corto o largo. Ejemplos: /25  ó  /255.255.255.128
4 - El programa debe verificar que la entrada sea válida (IP válido, Máscara válida).
5 - El programa debe contar con una interfaz por consola
6 - Se deben utilizar los operadores & (AND) y | (OR) a nivel de bits.
7 - La salidas deben mostrarse en formato binario y decimal, en el mismo orden que los ejercicios vistos en clase


* Para el calculo del Network::

IP en binario
Mascara en binario
----------------------------
Network en binario
Network en decimal



* Para el calculo del Broadcast::

IP en binario
Complemento a uno de Mascara en binario
----------------------------
Broadcast en binario
Broadcast en decimal


Ejemplo para ejecutar el programa por consola. (Asegurense que es asi tal cual el nombre de su programa)
java CalculdoraRed 10.10.10.44/25


Nota: Escriban su programa de forma OO, reutilicen las partes que se puedan. Se espera código de calidad.
