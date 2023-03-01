package com.example.calculadoraimc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.calculadoraimc.databinding.ActivityImcCreateBinding
import java.math.BigDecimal
import java.math.RoundingMode
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ImcCreate : AppCompatActivity() {
    private var binding: ActivityImcCreateBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImcCreateBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        var toolbarIMC: Toolbar = findViewById(R.id.toolbar_create_imc)
        setSupportActionBar(toolbarIMC)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarIMC.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.Button?.setOnClickListener{
            imcCalculate()
        }
    }

    // Verifica se os valores foram inseridos
    private fun valid(): Boolean{
        var valido = true
        if(binding?.etHeight?.text.toString().isEmpty()){
            valido = false
        }else if (binding?.etWeight?.text.toString().isEmpty()) {
            valido = false
        }
            return valido
    }


    // Formula = peso / (altura*altura)

    private fun imcCalculate() {
        if (valid()) {
            val imcDao = (application as ItemApp).db.ImcDao()
            val peso: Float = binding?.etWeight?.text.toString().toFloat()
            val altura: Float =
                binding?.etHeight?.text.toString().toFloat() / 100 //Altura deve ser em m

            val imc = peso / (altura * altura)
            result(imc)
            addInfos(imcDao)
        }else {
            Toast.makeText(this@ImcCreate, "Insira valores validos para altura e peso",
            Toast.LENGTH_SHORT).show()
        }
    }

    private fun result(imc: Float){

        val imcValue = BigDecimal(imc.toDouble()).setScale(2,
            RoundingMode.HALF_EVEN).toString()
        val imcClassification: String

        if(imc < 18.5){
            imcClassification = "Magreza"
            binding?.tvType?.setTextColor(ContextCompat.getColor(this,R.color.LessRed))
        }else if(imc >= 18.5 && imc < 25){
            imcClassification = "Normal"
            binding?.tvType?.setTextColor(ContextCompat.getColor(this,R.color.Green))
        }else if(imc>=25 && imc<30){
            imcClassification = "Sobrepeso"
            binding?.tvType?.setTextColor(ContextCompat.getColor(this,R.color.Yellow))
        }else if(imc>=30 && imc<40){
            imcClassification = "Obesidade"
            binding?.tvType?.setTextColor(ContextCompat.getColor(this,R.color.Orange))
        }else{
            imcClassification = "Obesidade Grave"
            binding?.tvType?.setTextColor(ContextCompat.getColor(this,R.color.Red))
        }

        binding?.tvResult?.text = imcValue
        binding?.tvType?.text = imcClassification
        binding?.llResult?.visibility = View.VISIBLE
    }


    private fun addInfos(imcDao: ImcDao){
        val peso = binding?.etWeight?.text.toString()
        val altura = binding?.etHeight?.text.toString()
        val imc = binding?.tvResult?.text.toString()

        val c = Calendar.getInstance()
        val dateTime = c.time
        val sdf = SimpleDateFormat("dd MM yyyy", Locale.getDefault())
        val date = sdf.format(dateTime)

        lifecycleScope.launch{
            imcDao.insert(ImcEntity(date = date,peso = peso, altura = altura, imc = imc))
            Toast.makeText(applicationContext, "IMC salvo", Toast.LENGTH_SHORT).show()
        }
    }



}