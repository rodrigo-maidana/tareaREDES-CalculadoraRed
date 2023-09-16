public class NetworkValidator {

    public boolean isValidLongMask(String subnetMask) {
        String[] parts = subnetMask.split("/");
        if (parts.length == 2) {
            String subnetMaskIP = parts[1];
            String[] subnetMaskParts = subnetMaskIP.split("\\.");

            if (subnetMaskParts.length == 4) {
                int byteAnterior = -1;
                for (String parte : subnetMaskParts) {
                    int octeto = Integer.parseInt(parte);
                    if (octeto < 0 || octeto > 255 || (byteAnterior != -1 && octeto < byteAnterior)) {
                        return false;
                    }
                    byteAnterior = octeto;
                }
                return true;
            }
        }

        try {
            String[] subnetMaskLarga = subnetMask.split("\\.");
            if (subnetMaskLarga.length != 4) {
                return false;
            }
            int subnetMaskCIDR = 0;
            for (String parte : subnetMaskLarga) {
                int octeto = Integer.parseInt(parte);
                while (octeto > 0) {
                    subnetMaskCIDR += octeto & 1;
                    octeto >>= 1;
                }
            }
            return subnetMaskCIDR >= 0 && subnetMaskCIDR <= 32;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String convertSubnetMaskToPrefixLength(String subnetMask) {
        String[] parts = subnetMask.split("\\.");
        int prefixLength = 0;

        for (String part : parts) {
            int octet = Integer.parseInt(part);
            while (octet > 0) {
                prefixLength += octet & 1;
                octet >>= 1;
            }
        }

        return String.valueOf(prefixLength);
    }

    public int calculateSubnetMaskValue(int prefixLength) {
        return 0xFFFFFFFF << (32 - prefixLength);
    }

    public byte[] calculateSubnetMaskBytes(int subnetMaskValue) {
        return new byte[] {
                (byte) (subnetMaskValue >> 24),
                (byte) (subnetMaskValue >> 16),
                (byte) (subnetMaskValue >> 8),
                (byte) subnetMaskValue
        };
    }

    public byte[] calculateNetworkAddress(byte[] ipBytes, byte[] subnetMaskBytes) {
        byte[] networkBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            networkBytes[i] = (byte) (ipBytes[i] & subnetMaskBytes[i]);
        }
        return networkBytes;
    }

    public byte[] calculateBroadcastAddress(byte[] ipBytes, byte[] subnetMaskBytes) {
        byte[] broadcastBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            broadcastBytes[i] = (byte) (ipBytes[i] | ~subnetMaskBytes[i]);
        }
        return broadcastBytes;
    }

    public byte[] calculateInverseSubnetMaskBytes(byte[] subnetMaskBytes) {
        byte[] inverseSubnetMaskBytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            inverseSubnetMaskBytes[i] = (byte) ~subnetMaskBytes[i];
        }
        return inverseSubnetMaskBytes;
    }

    // Nueva función para convertir bytes de IP en una representación binaria
    public String ipToBinary(byte[] ipBytes) {
        StringBuilder binaryIp = new StringBuilder();
        for (byte b : ipBytes) {
            String binaryOctet = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            binaryIp.append(binaryOctet).append(".");
        }
        return binaryIp.deleteCharAt(binaryIp.length() - 1).toString();
    }
}
