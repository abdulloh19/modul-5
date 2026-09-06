package uz.pdp;

import java.util.Base64;

public class EncodeDecode {
    static void main(String[] args) {
        Base64.Encoder encoder  = Base64.getEncoder();
        String s = "Hello G64";
        byte[] encode = encoder.encode(s.getBytes());

        String base64 = new String(encode);
        System.out.println(base64);

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(base64);
        System.out.println(new String(decode));
    }
}
