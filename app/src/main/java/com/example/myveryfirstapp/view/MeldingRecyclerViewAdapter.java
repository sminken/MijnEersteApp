package com.example.myveryfirstapp.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myveryfirstapp.R;
import com.example.myveryfirstapp.domain.Melding;

import java.util.ArrayList;
import java.util.List;

public class MeldingRecyclerViewAdapter extends RecyclerView.Adapter<MeldingRecyclerViewAdapter.MeldingDetailViewHolder> {

    private static final String TAG = "MeldingRecViewAdapter";


    private List<Melding> mlijstMeldingen;

    private Context mContext;

    public MeldingRecyclerViewAdapter(List<Melding> lijstMeldingen, Context mContext) {
        this.mlijstMeldingen = lijstMeldingen;
        this.mContext = mContext;
    }

    @Override
    public MeldingDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.melding_lijst_item, parent, false);
        MeldingDetailViewHolder holder = new MeldingDetailViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MeldingDetailViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.meldingTekst.setText(mlijstMeldingen.get(position).getTekst());
        holder.meldingGebruiker.setText(mlijstMeldingen.get(position).getGebruiker());
        holder.meldingDatumTijd.setText(mlijstMeldingen.get(position).getDatumTijd());

        holder.meldingenOntvangerParentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: clicked on" + mlijstMeldingen.get(position));




                Toast.makeText(mContext, mlijstMeldingen.get(position).getTekst(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlijstMeldingen.size();
    }


    public class MeldingDetailViewHolder extends RecyclerView.ViewHolder {

        TextView meldingTekst;
        TextView meldingGebruiker;
        TextView meldingDatumTijd;
        ConstraintLayout meldingenOntvangerParentLayout;

        public MeldingDetailViewHolder(View itemView) {
            super(itemView);
            meldingTekst = itemView.findViewById(R.id.melding_tekst);
            meldingGebruiker = itemView.findViewById(R.id.melding_gebruiker);
            meldingDatumTijd = itemView.findViewById(R.id.melding_datumTijd);
            meldingenOntvangerParentLayout = itemView.findViewById(R.id.meldingen_ontvanger_parent_layout);
        }
    }
}
