package com.example.myveryfirstapp.data.entity;

public class MeldingEntity {
    private String datum = null;
    private String gebruiker = null;
    private String tekst = null;
    private String tijd = null;

    public MeldingEntity() {
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(String gebruiker) {
        this.gebruiker = gebruiker;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String getTijd() {
        return tijd;
    }

    public void setTijd(String tijdstip) {
        this.tijd = tijdstip;
    }
}
