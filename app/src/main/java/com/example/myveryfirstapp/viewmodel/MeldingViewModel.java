package com.example.myveryfirstapp.viewmodel;
/**
 * Deze klasse beheert de instantie van een viewmodel voor meldingen
 * Voor meer info zie: https://developer.android.com/reference/android/arch/lifecycle/ViewModel
 * @author Ron van den Enden
 */

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.myveryfirstapp.data.repository.FirebaseDatabaseRepository;
import com.example.myveryfirstapp.data.repository.MeldingRepository;
import com.example.myveryfirstapp.domain.Melding;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MeldingViewModel extends ViewModel {

    /*
    Livedata klasse voor meldingen aanmaken
     */
    private MutableLiveData<List<Melding>> liveDataMeldingen;

    /*
    Maak repo aan voor meldingen
     */
    private MeldingRepository meldingRepository = new MeldingRepository();

    public LiveData<List<Melding>> haalMeldingen() {
        if (liveDataMeldingen == null) {
            liveDataMeldingen = new MutableLiveData<>();
            laadMeldingen();
        }
        return liveDataMeldingen;
    }

    @Override
    /*
    When the owner activity is finished, the framework calls the ViewModel objects's onCleared() method so that it can clean up resources.
     */
    protected void onCleared() {
        meldingRepository.removeListener();
    }

    private void laadMeldingen() {
        /*
        Listener toevoegen aan repo voor meldingen
         */
        meldingRepository.addListener(new FirebaseDatabaseRepository.FirebaseDatabaseRepositoryCallback<Melding>() {
            @Override
            public void onSuccess(List<Melding> result) {
                /*
                Meldingen sorteren: jongste voorop
                 */
                Collections.sort(result, new Comparator<Melding>() {
                    @Override
                    public int compare(Melding m1, Melding m2) {
                        return 0-m1.getDatumTijd().compareTo(m2.getDatumTijd());
                    }
                });
                liveDataMeldingen.setValue(result);
            }

            @Override
            public void onError(Exception e) {
                liveDataMeldingen.setValue(null);
            }



        });
    }

    public void voegMeldingToe(Melding melding) {
        meldingRepository.voegMeldingToe(melding);
    }
}
