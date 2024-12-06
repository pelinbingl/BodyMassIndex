package com.example.bodymassindex

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bodymassindex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // "Hesapla" butonuna tıklama işlemi
        binding.btnHesapla.setOnClickListener {
            // Boy ve kilo değerlerini alıyoruz
            val heightInCm = binding.etBoy.text.toString().toDoubleOrNull()
            val weightInKg = binding.etKilo.text.toString().toDoubleOrNull()

            // Boy veya kilo boşsa hata mesajı gösteriliyor
            if (heightInCm == null || weightInKg == null) {
                Toast.makeText(this, "Boy ve/veya kilo boş bırakılamaz.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Boy 200 cm'nin üzerindeyse hata mesajı gösteriliyor
            if (heightInCm > 200) {
                Toast.makeText(this, "Boy 200 cm'den fazla olamaz.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cinsiyet seçimi kontrol ediliyor
            val selectedGenderId = binding.rgGender.checkedRadioButtonId
            if (selectedGenderId == -1) {
                Toast.makeText(this, "Lütfen cinsiyetinizi seçin.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cinsiyet belirleniyor
            val gender = when (selectedGenderId) {
                binding.rbKadin.id -> "Kadın"
                binding.rbErkek.id -> "Erkek"
                else -> "Bilinmeyen"
            }

            // BMI (Vücut Kitle İndeksi) hesaplanıyor
            val heightInM = heightInCm / 100
            val bmi = weightInKg / (heightInM * heightInM)

            // BMI sonucu belirleniyor
            val result = when {
                bmi < 18.5 -> "Zayıf"
                bmi < 25 -> "Normal"
                bmi < 30 -> "Fazla Kilolu"
                else -> "Obez"
            }

            // Sonuç mesajı hazırlanıyor
            val toastMessage = "Cinsiyet: $gender\nBMI: %.2f\nSonuç: $result".format(bmi)

            // Mesaj, Toast ve AlertDialog olarak gösteriliyor
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show()
            AlertDialog.Builder(this)
                .setTitle("BMI Hesaplama Sonucu")
                .setMessage(toastMessage)
                .setPositiveButton("Tamam") { dialog, _ -> dialog.dismiss() }
                .show()
        }
    }
}
