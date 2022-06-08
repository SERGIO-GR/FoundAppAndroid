package com.example.foundapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class publi extends AppCompatActivity {
    private ImageView imagepub;
   private EditText nombrepub,despub;
   private Button agregarpubli;
   private RadioButton Oper,Oenc;
   private static final int Gallery_Pick=1;
   private Uri imageUri;
   private String productoRandomKey,downloadUri;
   private StorageReference ObjImagenRef;
   private DatabaseReference objref;
   private ProgressDialog dialog;
   private String Categoria,nom,des,CurrentDate,CurrentTime,tipo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publi);

        imagepub=findViewById(R.id.imagepub);
        Categoria= getIntent().getExtras().get("categoria").toString();
        ObjImagenRef= FirebaseStorage.getInstance().getReference().child("Imagenes Publicaciones");
        objref= FirebaseDatabase.getInstance().getReference().child("Objetos");
        nombrepub=findViewById(R.id.nombrepub);
        despub=findViewById(R.id.despub);
        Oenc=findViewById(R.id.Oencontrado);
        Oper=findViewById(R.id.Operdido);
        agregarpubli=findViewById(R.id.agregarpubli);
        dialog=new ProgressDialog(this);

        imagepub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbrirGaleria();
            }
        });
        agregarpubli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarObjeto();
            }
        });
    }

    private void AbrirGaleria() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/");
        startActivityForResult(intent,Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==Gallery_Pick && resultCode==RESULT_OK && data!=null){
            imageUri=data.getData();
            imagepub.setImageURI(imageUri);
        }
    }

    private void ValidarObjeto() {
        if(Oper.isChecked()){
        nom=nombrepub.getText().toString();
        des=despub.getText().toString();
        StringBuffer result = new StringBuffer();
        result.append(Oper.getText().toString()).append("");
        tipo= result.toString();
        if (imageUri==null){
            Toast.makeText(this, "Primero agrega una imagen", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(nom)){
            Toast.makeText(this, "Debes ingresar el nombre del producto", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(des)){
            Toast.makeText(this, "Debes ingresar la descripcion del producto", Toast.LENGTH_SHORT).show();

        }else{
         GuardarInfo();
        }


        }else if (Oenc.isChecked()){
            nom=nombrepub.getText().toString();
            des=despub.getText().toString();
            StringBuffer result = new StringBuffer();
            result.append(Oenc.getText().toString()).append("");
            tipo= result.toString();
            if (imageUri==null){
                Toast.makeText(this, "Primero agrega una imagen", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(nom)){
                Toast.makeText(this, "Debes ingresar el nombre del producto", Toast.LENGTH_SHORT).show();
            }else if(TextUtils.isEmpty(des)){
                Toast.makeText(this, "Debes ingresar la descripcion del producto", Toast.LENGTH_SHORT).show();

            }else{
                GuardarInfo();
            }
        }
    }

    private void GuardarInfo() {


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat curreDateFormat=new SimpleDateFormat("MM-dd-yyyy");
        CurrentDate=curreDateFormat.format(calendar.getTime());

        SimpleDateFormat CurrentTimeFormat=new SimpleDateFormat("HH:mm:ss");
        CurrentTime=CurrentTimeFormat.format(calendar.getTime());

        productoRandomKey = CurrentDate+CurrentTime;

        final StorageReference filePath = ObjImagenRef.child(imageUri.getLastPathSegment()+productoRandomKey+".jpg");
        final UploadTask uploadTask=filePath.putFile(imageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String mensaje=e.toString();
                Toast.makeText(publi.this, "Error", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(publi.this, "Imagen Guardada Exitosamente", Toast.LENGTH_SHORT).show();
                Task<Uri> uriTask= uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                       if (!task.isSuccessful()){
                           throw task.getException();
                       }
                       downloadUri = filePath.getDownloadUrl().toString();
                       return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            downloadUri = task.getResult().toString();
                            Toast.makeText(publi.this, "Imagen Guardada en Firebase", Toast.LENGTH_SHORT).show();
                            GuardarenFirebase();
                        }
                    }
                });
            }
        });
    }


    private void GuardarenFirebase() {
        HashMap<String,Object> map=new HashMap<>();
        map.put("pid",productoRandomKey);
        map.put("fecha",CurrentDate);
        map.put("hora",CurrentTime);
        map.put("descripcion",des);
        map.put("tipo",tipo);
        map.put("nombre",nom);
        map.put("imagen",downloadUri);
        map.put("categoria",Categoria);

        objref.child(productoRandomKey).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent i = new Intent(publi.this,Index.class);
                    startActivity(i);
                    dialog.dismiss();
                    Toast.makeText(publi.this, "Solicitud Exitosa", Toast.LENGTH_SHORT).show();

                }else{
                    dialog.dismiss();
                    String mensaje=task.getException().toString();
                    Toast.makeText(publi.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}
