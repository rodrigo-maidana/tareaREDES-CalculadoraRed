import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class CalculadoraRed {
    public static void main(String[] args) throws UnknownHostException {
        NetworkValidator validator = new NetworkValidator();
        Scanner scanner = new Scanner(System.in);

        System.out.print(
                "Ingresa la dirección IP y la máscara de red (Ej. 192.168.0.16/24 o 192.168.0.16/255.255.255.0): ");
        String input = scanner.nextLine();
        scanner.close();

        String[] parts = input.split("/");
        if (parts.length != 2) {
            System.err.println("Ingreso de datos incorrecto, debe ser dirección IP y máscara.");
            return;
        }

        String ipAddress = parts[0];
        String maskOrPrefix = parts[1];
        int maskLength = 0;

        if (maskOrPrefix.contains(".")) {
            if (validator.isValidLongMask(maskOrPrefix)) {
                maskLength = Integer.parseInt(validator.convertSubnetMaskToPrefixLength(maskOrPrefix));
            } else {
                System.err.println("La máscara en formato largo no es válida.");
                return;
            }
        } else {
            maskLength = Integer.parseInt(parts[1]);
        }

        String[] ipParts = ipAddress.split("\\.");
        if (ipParts.length != 4) {
            System.err.println("La dirección IP debe tener 4 partes separadas por puntos.");
            return;
        }

        byte[] ipBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            int octet = Integer.parseInt(ipParts[i]);
            if (octet < 0 || octet > 255) {
                System.err.println("Las partes de la dirección IP deben estar en el rango de 0 a 255.");
                return;
            }
            ipBytes[i] = (byte) octet;
        }

        System.out.println("Para el cálculo del Network::\n");

        System.out.println("IP en binario: " + validator.ipToBinary(ipBytes));
        int subnetMaskValue = validator.calculateSubnetMaskValue(maskLength);
        byte[] subnetMaskBytes = validator.calculateSubnetMaskBytes(subnetMaskValue);
        System.out.println("Máscara en binario: " + validator.ipToBinary(subnetMaskBytes));

        System.out.println("----------------------------");
        byte[] networkBytes = validator.calculateNetworkAddress(ipBytes, subnetMaskBytes);
        System.out.println("Network en binario: " + validator.ipToBinary(networkBytes));
        System.out.println("Network en decimal: " + InetAddress.getByAddress(networkBytes).getHostAddress());

        System.out.println("\nPara el cálculo del Broadcast::\n");

        System.out.println("IP en binario: " + validator.ipToBinary(ipBytes));
        byte[] inverseSubnetMaskBytes = validator.calculateInverseSubnetMaskBytes(subnetMaskBytes);
        System.out.println("Complemento a uno de Máscara en binario: " + validator.ipToBinary(inverseSubnetMaskBytes));

        System.out.println("----------------------------");
        byte[] broadcastBytes = validator.calculateBroadcastAddress(ipBytes, subnetMaskBytes);
        System.out.println("Broadcast en binario: " + validator.ipToBinary(broadcastBytes));
        System.out.println("Broadcast en decimal: " + InetAddress.getByAddress(broadcastBytes).getHostAddress());
    }

}
