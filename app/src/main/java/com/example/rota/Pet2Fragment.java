package com.example.rota;

import static android.app.Activity.RESULT_OK;

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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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


public class Pet2Fragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private EditText petNameEditText;
    private EditText petPriceEditText;
    private EditText petPhoneEditText;
    private Button chooseImageButton;
    private ImageView petImage;
    private Button addPetButton;
    Button selladdbutton;
    private RecyclerView recyclerView;
    private ArrayList<Pet> petsList;
    private PetAdapter petAdapter;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private Uri imageUri;

    public Pet2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pet2, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        selladdbutton=view.findViewById(R.id.selladdbutton);
        petsList = new ArrayList<>();
        petAdapter = new PetAdapter(petsList);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(petAdapter);

        reference = FirebaseDatabase.getInstance().getReference("pets");
        storageReference = FirebaseStorage.getInstance().getReference("pets");

        selladdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CatsellActivity.class);
                startActivity(intent);
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

        return view;
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(getContext().getContentResolver().getType(uri));
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
            Glide.with(getContext()).load(pet.getImageUrl()).into(holder.petImage);

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