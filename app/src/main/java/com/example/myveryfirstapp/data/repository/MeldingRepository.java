package com.example.myveryfirstapp.data.repository;

import com.example.myveryfirstapp.data.entity.MeldingEntity;
import com.example.myveryfirstapp.data.mapper.MeldingMapper;
import com.example.myveryfirstapp.domain.Melding;

public class MeldingRepository extends FirebaseDatabaseRepository<Melding> {

    public MeldingRepository() {
        super(new MeldingMapper());
    }

    public void voegMeldingToe(Melding melding) {
        MeldingEntity meldingEntity = MeldingMapper.demap(melding);
        databaseReference.push().setValue(meldingEntity);

    }

    @Override
    protected String getRootNode() {
        return "meldingen";
    }
}
