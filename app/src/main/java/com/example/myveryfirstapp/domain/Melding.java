package com.example.myveryfirstapp.domain;

public class Melding {
    private String datumTijd = null;
    private String gebruiker = null;
    private String tekst = null;

    public Melding(){};

    public Melding(String datumTijd, String gebruiker, String tekst) {
        this.datumTijd = datumTijd;
        this.gebruiker = gebruiker;
        this.tekst = tekst;
    }

    public String getDatumTijd() {
        return datumTijd;
    }

    public String getGebruiker() {
        return gebruiker;
    }

    public String getTekst() {
        return tekst;
    }
}
