package com.example.pharmamate.Fragments

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmamate.AddingScreen
import com.example.pharmamate.Database.MyDatabaseHelper
import com.example.pharmamate.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ItemsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ItemsFragment : Fragment() {

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
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_items, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myDB = MyDatabaseHelper(requireActivity())
        storeDataInArrays()


        medicineAdapter = MedicineAdapter(requireActivity(),requireActivity(),"item", uniqueIdList, namesList, priceList, quantityList, mfgList, expList)
        recyclerView.adapter = medicineAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

    }

    private fun storeDataInArrays() {
        val cursor: Cursor = myDB.getAllMedicines()
        while (cursor.moveToNext()) {
            uniqueIdList.add(cursor.getString(0))
            namesList.add(cursor.getString(1))
            priceList.add(cursor.getInt(2))
            quantityList.add(cursor.getInt(3))
            mfgList.add(cursor.getString(4))
            expList.add(cursor.getString(5))
        }
    }


    }