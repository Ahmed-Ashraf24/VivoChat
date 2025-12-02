package com.example.vivochat.data.dataSource.firebase_remote_datasource.firebase_utility

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseIstance {
     val firebaseDatabase = FirebaseDatabase.getInstance()
     val firestore = FirebaseFirestore.getInstance()
     val firebaseAuth= FirebaseAuth.getInstance()
}