package com.example.pertemuan13.model

data class Mahasiswa(
    val nim: String,
    val nama: String,
    val alamat: String,
    val jenisKelamin: String,
    val kelas: String,
    val angkatan: String,
    val judulSkripsi: String,
    val dosenPembimbing1: String,
    val dosenPembimbing2: String
){
    constructor() : this("", "", "", "", "", "", "", "", "")
}