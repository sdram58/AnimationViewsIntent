package com.catata.animationviewsintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.catata.animationviewsintent.constantes.Constantes;
import com.catata.animationviewsintent.model.GestionPeliculas;
import com.catata.animationviewsintent.model.Pelicula;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {



    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.miGrid);

        GestionPeliculas.crearPeliculas();


        MyAdapter myAdapter = new MyAdapter(R.id.miGrid, GestionPeliculas.PELICULAS,this);

        gridView.setAdapter(myAdapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Pelicula p = (Pelicula) view.getTag();
        Intent intent = new Intent(this, SecondActivity.class);
        Bundle extras = new Bundle();
        extras.putString(Constantes.ID_PELICULA, p.getId());

        intent.putExtras(extras);

        ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                new Pair<View,String>(view.findViewById(R.id.ivFoto), Constantes.SAHRED_VIEW_FOTO),
                new Pair<View,String>(view.findViewById(R.id.tvAutor), Constantes.SAHRED_VIEW_AUTOR),
                new Pair<View,String>(view.findViewById(R.id.tvTitulo), Constantes.SAHRED_VIEW_TITLE)
        );

        ActivityCompat.startActivity(this,intent,activityOptions.toBundle());

        //startActivity(intent);
    }


    private class MyAdapter extends ArrayAdapter{
        MainActivity parent;
        Context c;
        List<Pelicula> peliculas;
        MyAdapter(int view, List<Pelicula> peliculas, MainActivity parent){
            super(parent.getBaseContext(),view,peliculas);
            this.c = parent.getBaseContext();
            this.peliculas = peliculas;
            this.parent = parent;

        }
        @Override
        public int getCount() {
            return super.getCount();
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_view, null);
            TextView tvAutor = (TextView) v.findViewById(R.id.tvAutor);
            TextView tvTitulo = (TextView) v.findViewById(R.id.tvTitulo);
            ImageView ivPortada = (ImageView) v.findViewById(R.id.ivFoto);

            tvAutor.setText(peliculas.get(i).getAutor());
            tvTitulo.setText(peliculas.get(i).getTitulo());
            String url = peliculas.get(i).getUrl_portada();
            Picasso.get()
                    .load(url)
                    //.resize(60, 100)
                    //.centerCrop()
                    .noPlaceholder()
                    .into(ivPortada);
            v.setTag(peliculas.get(i));
            return v;
        }


    }
}