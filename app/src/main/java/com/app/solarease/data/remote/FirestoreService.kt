package com.app.solarease.data.remote

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirestoreService @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun <T> getDocuments(
        collectionPath: String,
        parser: (DocumentSnapshot) -> T?
    ): List<T> {
        return try {
            val snapshot = firestore.collection(collectionPath).get().await()
            parseDocuments(snapshot, parser)
        } catch (e: Exception) {
            Log.e("FirestoreService", "Error getting $collectionPath", e)
            emptyList()
        }
    }

    suspend fun <T> getDocumentById(
        collectionPath: String,
        documentId: String,
        parser: (DocumentSnapshot) -> T?
    ): T? {
        return try {
            val doc = firestore.collection(collectionPath).document(documentId).get().await()
            parser(doc)
        } catch (e: Exception) {
            Log.e("FirestoreService", "Error getting $documentId from $collectionPath", e)
            null
        }
    }

    private fun <T> parseDocuments(
        snapshot: QuerySnapshot,
        parser: (DocumentSnapshot) -> T?
    ): List<T> {
        return snapshot.documents.mapNotNull { doc ->
            parser(doc).also { result ->
                if (result == null) {
                    Log.w("FirestoreService", "Failed to parse document ${doc.id}")
                }
            }
        }
    }
}
