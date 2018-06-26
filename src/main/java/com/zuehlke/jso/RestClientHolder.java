package com.zuehlke.jso;

import javax.ws.rs.client.Client;

public class RestClientHolder {

    private static Client _client;

    static Client getClient() {
        return _client;
    }

    static void setClient(Client client) {
        _client = client;
    }
}
