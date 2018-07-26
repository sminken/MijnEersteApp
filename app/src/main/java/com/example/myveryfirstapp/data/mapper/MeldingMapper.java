package com.example.myveryfirstapp.data.mapper;

import com.example.myveryfirstapp.data.entity.MeldingEntity;
import com.example.myveryfirstapp.domain.Melding;
import com.google.firebase.database.DataSnapshot;

public class MeldingMapper extends FirebaseMapper<MeldingEntity, Melding> {

    @Override
    public Melding map(DataSnapshot dataSnapshot) {
        MeldingEntity meldingEntity =  dataSnapshot.getValue(MeldingEntity.class);
//        Melding melding = new Melding();
//        melding.setDatum(meldingEntity.getDatum());
//        melding.setGebruiker(meldingEntity.getGebruiker());
//        melding.setTekst(meldingEntity.getTekst());
//        melding.setTijdstip(meldingEntity.getTijd());

        Melding melding = new Melding(meldingEntity.getDatum(), meldingEntity.getGebruiker(),
                                      meldingEntity.getTekst(), meldingEntity.getTijd());
        return melding;
    }
}
