package com.example.pertemuan11_konekin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pertemuan11_konekin.databinding.ActivityMainBinding
import com.example.pertemuan11_konekin.databinding.ListItemBinding
import com.example.pertemuan11_konekin.model.Data
import com.example.pertemuan11_konekin.model.Users
import com.example.pertemuan11_konekin.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = ApiClient.getInstance()
        val response = client.getAllUsers()
        val userList = ArrayList<Data>()

        response.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                for (i in response.body()!!.data) {
                    var dataUser = Data(id = i.id, email = i.email, firstName = i.firstName, lastName = i.lastName, avatar = i.avatar)
                    userList.add(dataUser)
                }
                binding.listItem.layoutManager = LinearLayoutManager(this@MainActivity)
//                Toast.makeText(this@MainActivity, userList[0].email, Toast.LENGTH_SHORT).show()
                val adapterList = ItemAdapter(userList) { Data ->
                    val intentToSecondActivity = Intent(this@MainActivity, MainActivity2::class.java)
                    intentToSecondActivity.putExtra("EXTRA_EMAIL", Data.email)
                    startActivity(intentToSecondActivity)
                }
                binding.listItem.adapter = adapterList
//                Toast.makeText(this@MainActivity, adapterList.getItemCount().toString(), Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<Users>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Koneksi error",
                    Toast.LENGTH_LONG).show()
            }
        })

    }
}

typealias OnClickItem = (Data) -> Unit

class ItemAdapter(
    private val listItem: ArrayList<Data>,
    private val onClickItem: OnClickItem
) : RecyclerView.Adapter<ItemAdapter.ItemListViewHolder>() {

    inner class ItemListViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data) {
            binding.email.text = data.email
            binding.name.text = data.firstName+" "+data.lastName
            Glide.with(binding.root.context)
                .load(data.avatar)
                .into(binding.image)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, MainActivity2::class.java).apply {
                    putExtra("EXTRA_EMAIL", data.email)
                    putExtra("EXTRA_FIRSTNAME", data.firstName)
                    putExtra("EXTRA_LASTNAME", data.lastName)
                    putExtra("EXTRA_AVATAR", data.avatar)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemListViewHolder(binding)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val data = listItem[position]
        Log.d("ItemAdapter", "Item at position $position: $data")
        holder.bind(data)
    }
}