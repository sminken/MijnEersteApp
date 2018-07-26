package com.example.myveryfirstapp.data.repository;

import com.example.myveryfirstapp.data.mapper.MeldingMapper;
import com.example.myveryfirstapp.domain.Melding;

public class MeldingRepository extends FirebaseDatabaseRepository<Melding> {

    public MeldingRepository() {
        super(new MeldingMapper());
    }

    @Override
    protected String getRootNode() {
        return "meldingen";
    }
}
