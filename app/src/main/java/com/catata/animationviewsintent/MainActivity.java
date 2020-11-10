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
    //Objeto de Tipo GridView
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView)findViewById(R.id.miGrid);

        //Llamamos al método estático que carga las películas
        GestionPeliculas.crearPeliculas();


        //Creamos nuestro adaptador
        MyAdapter myAdapter = new MyAdapter(this,R.id.miGrid, GestionPeliculas.PELICULAS);

        //Se lo pasamos al gridView
        gridView.setAdapter(myAdapter);

        //Indicamos el manejador de OnItemClickListener, decimos this, porque lo hemos implementado en la clase.
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Recuperamos el Tag y hacemos un casting a tipo Película
        Pelicula p = (Pelicula) view.getTag();

        Intent intent = new Intent(this, SecondActivity.class);

        //Le añadimos un extra que contendrá el ID de la película.
        Bundle extras = new Bundle();
        extras.putString(Constantes.ID_PELICULA, p.getId());

        intent.putExtras(extras);

        //Creamos Opciones de la actividad, en este caso crear una escena de transición
        //Le pasamos la actividad actual, y elementos de ti par, que contendrá la vista a hacer la animación y una etiqueta que la identifique en la otra Actividad
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

        //Constructor de nuestro Adaptador
        //Le pasamos el contexto, la vista padre (GridView) y un ArrayList
        MyAdapter(Context c, int view, List<Pelicula> peliculas){
            super(c,view,peliculas); //Llamamos al constructor de la clase padre
            this.c = c;
            this.peliculas = peliculas;
            this.parent = parent;

        }
        @Override
        public int getCount() {
            return super.getCount();
            //También podríamos haber hecho peliculas.size();
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View v;
            //Cargamos en la vista v, el layout de cada elemento del gridview
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_view, null);

            //Obtenemos referencia a cada uno de sus elementos
            TextView tvAutor = (TextView) v.findViewById(R.id.tvAutor);
            TextView tvTitulo = (TextView) v.findViewById(R.id.tvTitulo);
            ImageView ivPortada = (ImageView) v.findViewById(R.id.ivFoto);

            //Asigamnos valor a las vistas en función de la posición i
            tvAutor.setText(peliculas.get(i).getAutor());
            tvTitulo.setText(peliculas.get(i).getTitulo());
            String url = peliculas.get(i).getUrl_portada();

            //Usamos Picasso para añadir una imagen de internet a un imageView
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