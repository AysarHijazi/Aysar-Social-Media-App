package com.example.rota;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CatsellActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    Button selladdbutton;
    private EditText petNameEditText;
    private EditText petPriceEditText;
    private EditText petPhoneEditText;
    private Button chooseImageButton;
    private ImageView petImage;
    private Button addPetButton;
    private RecyclerView recyclerView;
    private ArrayList<Pet> petsList;
    private PetAdapter petAdapter;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catsell);

        petNameEditText = findViewById(R.id.petName);
        petPriceEditText = findViewById(R.id.petPrice);
        petPhoneEditText = findViewById(R.id.petPhone);
        chooseImageButton = findViewById(R.id.chooseImageButton);
        petImage = findViewById(R.id.petImage);
        addPetButton = findViewById(R.id.addPetButton);

        petsList = new ArrayList<>();
        petAdapter = new PetAdapter(petsList);

        reference = FirebaseDatabase.getInstance().getReference("pets");
        storageReference = FirebaseStorage.getInstance().getReference("pets");

        addPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = petNameEditText.getText().toString().trim();
                String price = petPriceEditText.getText().toString().trim();
                String phone = petPhoneEditText.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    petNameEditText.setError("Pet name is required");
                    petNameEditText.requestFocus();
                } else if (TextUtils.isEmpty(price)) {
                    petPriceEditText.setError("Pet price is required");
                    petPriceEditText.requestFocus();
                } else if (TextUtils.isEmpty(phone)) {
                    petPhoneEditText.setError("Phone number is required");
                    petPhoneEditText.requestFocus();
                } else if (imageUri == null) {
                    Toast.makeText(CatsellActivity.this, "Please choose an image", Toast.LENGTH_SHORT).show();
                } else {
                    StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
                    fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Pet pet = new Pet(name, price, phone, uri.toString());
                                    reference.push().setValue(pet);
                                    Toast.makeText(CatsellActivity.this, "Pet added successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CatsellActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        chooseImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                petsList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Pet pet = snapshot.getValue(Pet.class);
                    petsList.add(pet);
                }
                petAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            petImage.setImageURI(imageUri);
        }
    }

    private class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {
        private ArrayList<Pet> petsList;

        public PetAdapter(ArrayList<Pet> petsList) {
            this.petsList = petsList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pet_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Pet pet = petsList.get(position);
            holder.petName.setText(pet.getName());
            holder.petPrice.setText(pet.getPrice());
            holder.petPhone.setText(pet.getPhone());
            Glide.with(CatsellActivity.this).load(pet.getImageUrl()).into(holder.petImage);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), PetDetailsActivity.class);
                    intent.putExtra("pet", new Gson().toJson(pet));
                    view.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return petsList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView petName;
            TextView petPrice;
            TextView petPhone;
            ImageView petImage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                petName = itemView.findViewById(R.id.petName);
                petPrice = itemView.findViewById(R.id.petPrice);
                petPhone = itemView.findViewById(R.id.petPhone);
                petImage = itemView.findViewById(R.id.petImage);
            }
        }
    }
}
