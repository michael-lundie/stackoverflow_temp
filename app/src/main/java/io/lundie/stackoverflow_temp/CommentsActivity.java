package io.lundie.stackoverflow_temp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

public class CommentsActivity extends AppCompatActivity {

    FirebaseFirestore db;
    CommentAdapter commentAdapter;

    ImageView profileImageView;
    EditText commentEditText;
    RecyclerView commentRecyclerView;
    Button addCommentButton; // Replaces the text view you are using

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commentEditText = findViewById(R.id.comment_edit_text);
        addCommentButton = findViewById(R.id.add_comment_button);
        commentRecyclerView = findViewById(R.id.comments_recycle_view);

        // Enables firestore debugging which will help a lot when trying to troubleshoot
        FirebaseFirestore.setLoggingEnabled(true);

        // We are now setting up our query directly within the OnCreate method.
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("CommentDetails").orderBy("timestamp").limit(50);

        FirestoreRecyclerOptions<Comment> options = new FirestoreRecyclerOptions.Builder<Comment>()
                .setQuery(query, Comment.class)
                .build();

        // Setting up the recycle adapter in onCreate
        commentAdapter = new CommentAdapter(options);
        commentRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        commentRecyclerView.setAdapter(commentAdapter);

        // Set up your onClickListener just as before.
        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Note that the previous null check is unsuccessful. Previously, the object instance
                // was being checked, and not the contents of the edit text. This resolves that issue. (:
                if (commentEditText.toString().isEmpty()) {
                    Toast.makeText(CommentsActivity.this, "Comment can't be empty", Toast.LENGTH_SHORT).show();
                } else {
                    String commentText = commentEditText.getText().toString();
                    CollectionReference commentColRef = FirebaseFirestore.getInstance().collection("CommentDetails");
                    commentColRef.add(new Comment(commentText));
                    Toast.makeText(CommentsActivity.this, "Commented", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        commentAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        commentAdapter.stopListening();
    }
}