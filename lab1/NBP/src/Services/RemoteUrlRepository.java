package Services;

import InterfacesServices.IRemoteRepository;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class RemoteUrlRepository implements IRemoteRepository {

    @Override
    public byte[] getUrlBytes(String urlString) throws Exception {
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try (InputStream inputStream = connection.getInputStream();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            return outputStream.toByteArray();

        } catch (java.io.IOException e) {
            e.printStackTrace();
            throw new Exception("Błąd pobierania danych z URL: " + urlString, e);
        }
    }
}
