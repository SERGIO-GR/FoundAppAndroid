package com.example.foundapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.net.BindException;

public class Index extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    DatabaseReference objref;
    FirebaseAuth firebaseAuth;
    RecyclerView reciclerMenu;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        objref = FirebaseDatabase.getInstance().getReference().child("Objetos");
        reciclerMenu = findViewById(R.id.recicler_menu);
        reciclerMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        reciclerMenu.setLayoutManager(layoutManager);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.frmInicio);
        navigationView = findViewById(R.id.nvView);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();


        DrawerLayout drawerLayout = findViewById(R.id.frmInicio);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);




    FirebaseRecyclerOptions<Objetos> options = new FirebaseRecyclerOptions.Builder<Objetos>().setQuery(objref, Objetos.class).build();
    FirebaseRecyclerAdapter<Objetos, ObjetoViewHolder> adapter = new FirebaseRecyclerAdapter<Objetos, ObjetoViewHolder>(options) {
        @Override
        protected void onBindViewHolder(@NonNull ObjetoViewHolder holder, int position, @NonNull Objetos model) {
            holder.objetonom.setText(model.getNombre());
            holder.objetodes.setText(model.getDescripcion());
            holder.objetotip.setText(model.getTipo());
            holder.objetocat.setText(model.getCategoria());
            Picasso.get().load(model.getImagen()).into(holder.ObjetoImagen);

        }

        @NonNull
        @Override
        public ObjetoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.publi_layout, viewGroup, false);
            ObjetoViewHolder holder = new ObjetoViewHolder(view);
            return holder;
        }

    };
        reciclerMenu.setAdapter(adapter);
        adapter.startListening();
}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         return  super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.CS){
            FirebaseAuth.getInstance().signOut();
            gologin();
        }else if (id==R.id.crearpubli){
            Cpubli();
        }

        DrawerLayout drawerLayout=(DrawerLayout)findViewById(R.id.frmInicio);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void Cpubli(){
        Intent i = new Intent(Index.this,CrearPubli.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


    private void gologin() {
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }




}