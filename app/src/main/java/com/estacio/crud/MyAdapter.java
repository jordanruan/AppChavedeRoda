package com.estacio.crud;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Model> mList;

    Context context;

    public MyAdapter(Context context, ArrayList<Model> mList){
        this.mList = mList;
        this.context = context;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Model model = mList.get(position);
        holder.placa.setText(model.getPlaca());
        holder.modelo.setText(model.getModelo());
        holder.problema.setText(model.getProblema());
        holder.servico.setText(model.getServico());
        holder.cor.setText(model.getCor());


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView placa, modelo, problema, servico, cor, option;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            placa = itemView.findViewById(R.id.placa_txt);
            modelo = itemView.findViewById(R.id.modelo_txt);
            problema = itemView.findViewById(R.id.problema_txt);
            servico = itemView.findViewById(R.id.servico_txt);
            cor = itemView.findViewById(R.id.cor_txt);
            option = itemView.findViewById(R.id.txt_option);


        }
    }

}
