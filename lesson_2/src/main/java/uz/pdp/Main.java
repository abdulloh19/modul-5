package uz.pdp;

import java.util.Base64;

public class Main {
    static void main(String[] args) {
        User user = new User();
        user.setReligion("islom");
        user.setId(2);
        user.setUsername("zubayr");
        user.setEmail("zubayrdev@gmail.com");
        user.setAge(22);
        /*User user = User.builder()
                .religion("islom")
                .id(1)
                .username("zubayr")
                .email("zubayrdev@gmail.com")
                .age(18)
                .build();*/
        Base64.Encoder encoder = Base64.getEncoder();
        String bytes = user.toString();
        byte[] encode = encoder.encode(bytes.getBytes());
        String base64 = new String(encode);
        System.out.println(base64);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(base64);
        String decoded = new String(decode);
        System.out.println(decoded);
    }
}
