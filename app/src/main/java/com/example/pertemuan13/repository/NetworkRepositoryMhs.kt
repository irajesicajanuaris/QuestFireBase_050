package com.example.pertemuan13.repository

import com.example.pertemuan13.model.Mahasiswa
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await


class NetworkRepositoryMhs(
    private val firestore: FirebaseFirestore
) : RepositoryMhs{
    override suspend fun insertMhs(mahasiswa: Mahasiswa) {
        try{
            firestore.collection("Mahasiswa").add(mahasiswa).await()
        } catch (e: Exception){
            throw Exception("Gagal menambahkkan data mahasiswa: ${e.message}")
        }
    }

    override fun getAllMhs(): Flow<List<Mahasiswa>> = callbackFlow {
        val mhsCollection = firestore.collection("Mahasiswa")
            .orderBy("nim", Query.Direction.DESCENDING)
            .addSnapshotListener{ value, error ->

                if (value != null) {
                    val mhsList = value.documents.mapNotNull {
                        it.toObject(Mahasiswa::class.java)!!
                    }
                    trySend(mhsList) // try send memberikan fungsi untuk mengirimkan data ke flow
                }
            }

        awaitClose{
            mhsCollection.remove()
        }
    }

    override fun getMhs(nim: String): Flow<Mahasiswa> = callbackFlow {
        val mhsDocument = firestore.collection("Mahasiswa")
            .document(nim)
            .addSnapshotListener{ value, error ->
                if (value != null) {
                    val mhs = value.toObject(Mahasiswa::class.java)!!
                }
            }
        awaitClose{
            mhsDocument.remove()
        }
    }

    override suspend fun deleteMhs(mahasiswa: Mahasiswa) {
        try {
            val querySnapshot = firestore.collection("Mahasiswa")
                .whereEqualTo("nim", mahasiswa.nim)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents[0]
                document.reference.delete().await()
            } else {
                throw Exception("Data tidak ditemukan")
            }
        } catch (e: Exception) {
            throw Exception("Gagal menghapus data mahasiswa: ${e.message}")
        }
    }

    override suspend fun updateMhs(mahasiswa: Mahasiswa) {
        try {
            firestore.collection("Mahasiswa")
                .document(mahasiswa.nim)
                .set(mahasiswa)
                .await()
        } catch (e: Exception){
            throw Exception("Gagal mengupdate data mahasiswa: ${e.message}")
        }
    }
}