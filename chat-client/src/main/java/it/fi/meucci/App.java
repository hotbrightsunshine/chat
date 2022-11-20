package it.fi.meucci;

import java.io.IOException;
import java.net.Inet4Address;

public class App
{

    public static final int PORT = 7777;
    public static final Inet4Address ADDRESS = (Inet4Address) Inet4Address.getLoopbackAddress();

    public static void main( String[] args ) throws IOException {
        Client c = new Client(ADDRESS, PORT);
    }
}
