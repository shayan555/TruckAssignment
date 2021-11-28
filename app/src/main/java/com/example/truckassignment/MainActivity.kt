package com.example.truckassignment

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.truckassignment.Adapter.TruckAdapter
import com.example.truckassignment.Api.RetrofitInstance
import com.example.truckassignment.Api.TruckServices
import com.example.truckassignment.Model.Data
import com.example.truckassignment.Model.TruckModel
import com.google.gson.Gson
import retrofit2.Response
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    private lateinit var retService:TruckServices
    private lateinit var recyclerView: RecyclerView
    private lateinit var truckAdapter: TruckAdapter
    private lateinit var searchTruck:AutoCompleteTextView
    private lateinit var list:ArrayList<String>
    private lateinit var data:List<Data>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recycler_data)
        searchTruck = findViewById(R.id.searchTruck)
        list= ArrayList<String>()
        searchTruck.setAdapter(
            ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list
            )
        )
        recyclerView.layoutManager = LinearLayoutManager(this)

        retService = RetrofitInstance.getRetrofitInstance().create(TruckServices::class.java)
        searchTruck.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                filter(p0.toString())
            }

        })
        getData()
    }
    fun filter(text: String)
    {
        var filterName = ArrayList<Data>()
        for (s in data)
        {
            if(s.truckNumber?.toLowerCase()!!.contains(text.toLowerCase())) filterName.add(s)

        }
        truckAdapter.FilterList(filterName)
    }


    fun getData()
    {
        val responseLiveData:LiveData<Response<TruckModel>> = liveData {
            val response = retService.getTruckData()
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            data = it.body()?.data!!
            if (data != null) {
                for (i in 0..data.size - 1) {
                    data[i].truckNumber?.let { it1 -> list.add(it1) }
                }
            }
            Log.i("Data", data.toString())
            truckAdapter = data?.let { it1 -> TruckAdapter(this, it1) }!!
            recyclerView.adapter = truckAdapter
            truckAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_menu,menu)
        var menuItem = menu?.findItem(R.id.mySwitch)
        menuItem?.setActionView(R.layout.use_switch)
        var tb = menuItem?.actionView?.findViewById<ToggleButton>(R.id.toggleButton)

        tb?.setOnCheckedChangeListener { compoundButton, b ->
            if (b)
            {
                var intent  = Intent(this,MapsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }


}