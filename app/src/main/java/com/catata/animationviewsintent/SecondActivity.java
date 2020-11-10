package com.catata.animationviewsintent;

import android.content.Intent;
import android.os.Bundle;

import com.catata.animationviewsintent.constantes.Constantes;
import com.catata.animationviewsintent.model.GestionPeliculas;
import com.catata.animationviewsintent.model.Pelicula;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    Pelicula p;
    ImageView ivPortada;
    TextView tvTitulo,tvAutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //Referenciamos las vistas

        tvTitulo = (TextView)findViewById(R.id.tvTituloPeli);
        tvAutor = (TextView)findViewById(R.id.tvAutorPeli);
        ivPortada = (ImageView) findViewById(R.id.ivPortada);


        //Comprabamos que tenemos un extra y obtentemos el valor

        if(getIntent().hasExtra(Constantes.ID_PELICULA)){
            String id = getIntent().getStringExtra(Constantes.ID_PELICULA);

            //Con el ID del parámetro obtenemos la película
            p = GestionPeliculas.getPeliculaById(id);

            if(p!=null){

                //Establecemos las transiciones, asociando la vistas de las dos actividades gracias a las constantes
                ViewCompat.setTransitionName(ivPortada, Constantes.SAHRED_VIEW_FOTO);
                ViewCompat.setTransitionName(tvAutor, Constantes.SAHRED_VIEW_AUTOR);
                ViewCompat.setTransitionName(tvTitulo, Constantes.SAHRED_VIEW_TITLE);

                //Cargamos el contenido de las vistas.
                loadItems();

            }
        }


    }

    private void loadItems() {
        tvAutor.setText(p.getAutor());
        tvTitulo.setText(p.getTitulo());

        Picasso.get()
                .load(p.getUrl_portada())
                .into(ivPortada);
    }
}