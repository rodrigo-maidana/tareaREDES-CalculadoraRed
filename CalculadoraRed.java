import java.util.Scanner;

public class CalculadoraRed {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la dirección IP y la máscara en formato IP/MASK: ");
        String input = scanner.nextLine();

        String[] parts = input.split("/");
        String ipAddress = parts[0];
        int mask;

        // Parsear la máscara en formato corto o largo
        if (parts.length == 2) {
            try {
                mask = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Formato de máscara no válido.");
                return;
            }
        } else {
            System.out.println("Formato de entrada no válido.");
            return;
        }

        // Verificar que la máscara sea válida
        if (mask < 0 || mask > 32) {
            System.out.println("Máscara de red no válida.");
            return;
        }

        // Calcular network y broadcast
        String networkBinary = calculateNetwork(ipAddress, mask);
        String broadcastBinary = calculateBroadcast(ipAddress, mask);

        // Mostrar resultados en binario y decimal
        System.out.println("Para el cálculo del Network:\n");
        System.out.println("IP en binario: " + ipAddressToBinary(ipAddress));
        System.out.println("Máscara en binario: " + maskToBinary(mask));
        System.out.println("----------------------------");
        System.out.println("Network en binario: " + networkBinary);
        System.out.println("Network en decimal: " + binaryToDecimal(networkBinary));
        System.out.println("\nPara el cálculo del Broadcast:\n");
        System.out.println("IP en binario: " + ipAddressToBinary(ipAddress));
        System.out.println("Complemento a uno de la máscara en binario: " + invertMask(mask));
        System.out.println("----------------------------");
        System.out.println("Broadcast en binario: " + broadcastBinary);
        System.out.println("Broadcast en decimal: " + binaryToDecimal(broadcastBinary));
    }

    private static String calculateNetwork(String ipAddress, int mask) {
        StringBuilder network = new StringBuilder();
        String[] ipParts = ipAddress.split("\\.");
        for (int i = 0; i < 4; i++) {
            int octet = Integer.parseInt(ipParts[i]);
            int maskBit = (mask >= 8) ? 8 : mask;
            network.append(String.format("%8s", Integer.toBinaryString(octet)).replace(' ', '0').substring(0, maskBit));
            mask -= maskBit;
            if (mask <= 0)
                break;
        }
        while (network.length() < 32) {
            network.append("0");
        }
        return network.toString();
    }

    private static String calculateBroadcast(String ipAddress, int mask) {
        String network = calculateNetwork(ipAddress, mask);
        StringBuilder broadcast = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if (network.charAt(i) == '0')
                broadcast.append("1");
            else
                broadcast.append("0");
        }
        return broadcast.toString();
    }

    private static String ipAddressToBinary(String ipAddress) {
        String[] ipParts = ipAddress.split("\\.");
        StringBuilder binary = new StringBuilder();
        for (String part : ipParts) {
            String partBinary = String.format("%8s", Integer.toBinaryString(Integer.parseInt(part)))
                    .replace(' ', '0');
            binary.append(partBinary);
            binary.append(".");
        }
        return binary.deleteCharAt(binary.length() - 1).toString();
    }

    private static String maskToBinary(int mask) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if (i < mask)
                binary.append("1");
            else
                binary.append("0");
        }
        return binary.toString();
    }

    private static String invertMask(int mask) {
        StringBuilder invertedMask = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            if (i < mask)
                invertedMask.append("0");
            else
                invertedMask.append("1");
        }
        return invertedMask.toString();
    }

    private static int binaryToDecimal(String binary) {
        return Integer.parseInt(binary, 2);
    }
}
