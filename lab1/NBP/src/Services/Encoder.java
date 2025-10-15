package Services;

import InterfacesServices.IEncoder;

import java.nio.charset.Charset;

public class Encoder implements IEncoder {
    private static final Charset NBP_CHARSET = Charset.forName("ISO-8859-2");

    @Override
    public String encodeBytes(byte[] bytes) {
        return new String(bytes, NBP_CHARSET);

    }
}
