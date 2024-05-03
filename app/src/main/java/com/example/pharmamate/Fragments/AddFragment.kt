package com.example.pharmamate.Fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmamate.AddingScreen
import com.example.pharmamate.Database.MyDatabaseHelper
import com.example.pharmamate.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddFragment : Fragment() {

    private lateinit var addButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView

    private var uniqueIdList = ArrayList<String>()
    private var namesList = ArrayList<String>()
    private var priceList = ArrayList<Int>()
    private var quantityList = ArrayList<Int>()
    private var mfgList = ArrayList<String>()
    private val expList = ArrayList<String>()

    private lateinit var myDB : MyDatabaseHelper
    private lateinit var medicineAdapter : MedicineAdapter


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        addButton = view.findViewById(R.id.addBtn)
        recyclerView = view.findViewById(R.id.recyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDB = MyDatabaseHelper(requireActivity())
        storeDataInArrays()


        medicineAdapter = MedicineAdapter(requireActivity(),requireActivity(),"add", uniqueIdList, namesList, priceList, quantityList, mfgList, expList)
        recyclerView.adapter = medicineAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val addForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result ->
            if(result.resultCode == RESULT_OK){
                storeDataInArrays()
                medicineAdapter.notifyDataSetChanged()
            }
        }

        addButton.setOnClickListener{
            val intent = Intent(requireActivity(), AddingScreen::class.java)
            addForResult.launch(intent)
        }
    }
    private fun storeDataInArrays() {
        // Clear existing data before fetching new data
        uniqueIdList.clear()
        namesList.clear()
        priceList.clear()
        quantityList.clear()
        mfgList.clear()
        expList.clear()

        val cursor : Cursor = myDB.getAllMedicines()
        while (cursor.moveToNext()){
            uniqueIdList.add(cursor.getString(0)?:"")
            namesList.add(cursor.getString(1)?:"")
            priceList.add(cursor.getInt(2))
            quantityList.add(cursor.getInt(3))
            mfgList.add(cursor.getString(4)?:"")
            expList.add(cursor.getString(5)?:"")
        }
    }
}