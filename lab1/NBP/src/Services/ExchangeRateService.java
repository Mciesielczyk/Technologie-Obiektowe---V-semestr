package Services;

import InterfacesServices.IDocumentParser;
import InterfacesServices.IEncoder;
import InterfacesServices.IRemoteRepository;
import data.ExchangeTable;

public class ExchangeRateService {

    private final IRemoteRepository remoteRepository;
    private final IEncoder encoder;
    private final IDocumentParser documentParser;

    private ExchangeTable cachedTable = null;
    private static final String NBP_URL = "https://static.nbp.pl/dane/kursy/xml/lastA.xml";


    public ExchangeRateService(IRemoteRepository remoteRepository, IEncoder encoder, IDocumentParser documentParser) throws Exception {
        this.remoteRepository = remoteRepository;
        this.encoder = encoder;
        this.documentParser = documentParser;


        System.out.println("Inicjalizacja: Pobieranie aktualnej tabeli kursów NBP w serwisie...");
        this.cachedTable = this.fetchAndParseTableInternal();
        System.out.println("Dane pobrano pomyślnie i zapisano w pamięci podręcznej.");
    }

    private ExchangeTable fetchAndParseTableInternal() throws Exception {


        byte[] rawBytes = remoteRepository.getUrlBytes(NBP_URL);
        String rawData = encoder.encodeBytes(rawBytes);
        return documentParser.getTable(rawData);
    }


    public ExchangeTable fetchAndParseTable() throws Exception {
        if (this.cachedTable == null) {
            this.cachedTable = fetchAndParseTableInternal();
        }
        return this.cachedTable;
    }

    public void clearCache() {
        this.cachedTable = null;
    }
}