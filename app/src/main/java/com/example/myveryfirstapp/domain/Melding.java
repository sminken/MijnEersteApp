package com.example.myveryfirstapp.domain;

public class Melding {
    private String datum = null;
    private String gebruiker = null;
    private String tekst = null;
    private String tijdstip = null;

    public Melding(){};

    public Melding(String datum, String gebruiker, String tekst, String tijdstip) {
        this.datum = datum;
        this.gebruiker = gebruiker;
        this.tekst = tekst;
        this.tijdstip = tijdstip;
    }

    public String getDatum() {
        return datum;
    }

    public String getGebruiker() {
        return gebruiker;
    }

    public String getTekst() {
        return tekst;
    }

    public String getTijdstip() {
        return tijdstip;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public void setGebruiker(String gebruiker) {
        this.gebruiker = gebruiker;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public void setTijdstip(String tijdstip) {
        this.tijdstip = tijdstip;
    }
}
