package InterfacesServices;

import data.ExchangeTable;

public interface IDocumentParser {
    ExchangeTable getTable(String rawData) throws Exception;

}
