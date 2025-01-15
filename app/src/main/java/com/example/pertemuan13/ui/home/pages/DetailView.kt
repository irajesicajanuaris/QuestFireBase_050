package com.example.pertemuan13.ui.home.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail/{nim}"
    override val titleRes = "Detail Mahasiswa"
    const val NIM = "nim"
    val routeWithArgs = "$route/{$NIM}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    nim: String,
    onEditClick: (String) -> Unit = { },
    onBackClick: () -> Unit = { },
    modifier: Modifier = Modifier,
    viewModel: DetailMhsViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val mahasiswa = viewModel.uiState.detailUiEvent

    LaunchedEffect(nim) {
        viewModel.fetchDetailMahasiswa(nim)
    }

    val isLoading = viewModel.uiState.isLoading
    val isError = viewModel.uiState.isError
    val errorMessage = viewModel.uiState.errorMessage

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Detail Mahasiswa") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEditClick(mahasiswa.nim) },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit Mahasiswa")
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                else if (isError) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                else if (viewModel.uiState.isUiEventNotEmpty) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(8.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(text = "NIM: ${mahasiswa.nim}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Nama: ${mahasiswa.nama}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Alamat: ${mahasiswa.alamat}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Jenis Kelamin: ${mahasiswa.jenisKelamin}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Kelas: ${mahasiswa.kelas}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Angkatan: ${mahasiswa.angkatan}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Judul Skripsi: ${mahasiswa.judulSkripsi}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Dosen Pembimbing 1: ${mahasiswa.dosenPembimbing1}", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Dosen Pembimbing 2: ${mahasiswa.dosenPembimbing2}", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                }
            }
        }
    )
}