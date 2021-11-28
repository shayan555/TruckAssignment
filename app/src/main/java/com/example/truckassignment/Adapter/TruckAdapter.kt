package com.example.truckassignment.Adapter

import android.content.Context
import android.speech.tts.TextToSpeech
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.truckassignment.Model.Data
import com.example.truckassignment.R
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class TruckAdapter(var context: Context, var list:List<Data>): RecyclerView.Adapter<TruckAdapter.MyViewHolder>()
{
    private lateinit var date: Date
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TruckAdapter.MyViewHolder {
        var view= LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: TruckAdapter.MyViewHolder, position: Int) {

        var data = list[position]
        holder.truckNumber.text = data.truckNumber
        var days = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis()- data.lastWaypoint.createTime)
        holder.lastUpdate.text = Html.fromHtml("<font color=\"#DF655C\">${days.toString()}</font>"+" days ago")
        if (data.lastRunningState.truckRunningState.equals(1))
        {
            holder.runningState.text = "Running since last " + days +" ago"
        }
        else
        {
            holder.runningState.text = "Stopped since last " + days +" ago"
        }
        holder.speed.text = Html.fromHtml("<font color=\"#DF655C\">${data.lastWaypoint.speed.toString()}</font>"+" k/h")
    }

    override fun getItemCount(): Int {
        return list.size
    }
   fun FilterList(filterName:List<Data>)
   {
       this.list = filterName
       notifyDataSetChanged()
   }

    class MyViewHolder(var itemview : View):RecyclerView.ViewHolder(itemview){
        var truckNumber = itemview.findViewById<TextView>(R.id.truckNumber)
        var lastUpdate  = itemview.findViewById<TextView>(R.id.lastUpdate)
        var runningState = itemview.findViewById<TextView>(R.id.runningState)
        var speed  = itemview.findViewById<TextView>(R.id.speed)
    }

}