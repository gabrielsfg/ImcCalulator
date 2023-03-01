package com.example.calculadoraimc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.example.calculadoraimc.databinding.ActivityImcHistoryBinding
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ImcHistory : AppCompatActivity() {
    private var binding: ActivityImcHistoryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImcHistoryBinding.inflate((layoutInflater))
        setContentView(binding?.root)

        val toolbarHistory: Toolbar = findViewById(R.id.toolbar_history_imc)
        setSupportActionBar(toolbarHistory)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbarHistory.setNavigationOnClickListener {
            onBackPressed()
        }

        val imcDao = (application as ItemApp).db.ImcDao()
        lifecycleScope.launch{
            imcDao.getAll().collect(){
                val list = ArrayList(it)
                setupList(list, imcDao)
            }
        }

    }
    private fun setupList(imcList:ArrayList<ImcEntity>,
                          imcDao: ImcDao){
        if(imcList.isNotEmpty()) {
            val itemAdapter = ItemAdapter(imcList

            ) { deleteId ->
                lifecycleScope.launch {
                    imcDao.getAllById(deleteId).collect {
                        if (it != null) {
                            val imcToDelete = ImcEntity(it.id, it.date, it.peso, it.altura, it.imc)
                            deleteRecord(deleteId, imcDao, imcToDelete)
                        }
                    }
                }
            }


            binding?.rvHistory?.layoutManager = LinearLayoutManager(this)
            binding?.rvHistory?.adapter = itemAdapter
            binding?.rvHistory?.visibility = View.VISIBLE
            binding?.tvNoImc?.visibility= View.GONE
            binding?.llTable?.visibility = View.VISIBLE

        }else {
            binding?.rvHistory?.visibility = View.GONE
            binding?.tvNoImc?.visibility= View.VISIBLE
            binding?.llTable?.visibility = View.GONE

        }
    }

    private fun deleteRecord(id:Int, imcDao: ImcDao, imc: ImcEntity) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Deletar Informações")

        builder.setPositiveButton("Sim"){dialogInterface, _ ->
            lifecycleScope.launch{
                imcDao.delete(imc) // Passar a entidade a ser excluída
                Toast.makeText(
                    applicationContext,
                    "Informação deletada com sucesso!",
                    Toast.LENGTH_LONG
                ).show()
            }
            dialogInterface.dismiss()
        }

        builder.setNegativeButton("Não"){dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alertDialog: androidx.appcompat.app.AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}

