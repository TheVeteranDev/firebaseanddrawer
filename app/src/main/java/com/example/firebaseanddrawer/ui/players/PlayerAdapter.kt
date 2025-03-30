package com.example.firebaseanddrawer.ui.players

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseanddrawer.R
import com.google.firebase.firestore.FirebaseFirestore

class PlayerAdapter(
    private val players: MutableList<Player>
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>()  {
    // Get an instance of the firestore database
    private val db = FirebaseFirestore.getInstance()

    // Holder class to set individual items in the recycler view
    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.player_name)
        private val numberTextView: TextView = itemView.findViewById(R.id.player_number)
        private val positionTextView: TextView = itemView.findViewById(R.id.player_position)
        private val statusTextView: TextView = itemView.findViewById(R.id.player_status)
        private val photoImageView: ImageView = itemView.findViewById(R.id.player_photo)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.delete_button)

        // Bind function used to simplify onBindViewHolder
        fun bind(player: Player, adapter: PlayerAdapter) {
            nameTextView.text = player.name
            numberTextView.text = player.number.toString()
            positionTextView.text = player.position
            statusTextView.text = player.status

            // Find the drawable ID by the player photo string
            val drawableId = itemView.context.resources.getIdentifier(player.photo, "drawable", itemView.context.packageName)

            // If the id is 0, it was not found, set the no player photo as default
            if (drawableId == 0) {
                photoImageView.setImageResource(R.drawable.no_player_photo)
            } else {
                photoImageView.setImageResource(drawableId)
            }

            // Set on click listener to call deletePlayer function
            deleteButton.setOnClickListener {
                adapter.deletePlayer(player.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]

        // Keeps all the logic for setting values in the holder class
        holder.bind(player, this)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    // Exec a delete query on the database
    // If successful, find the index in the players array and remove it
    // Notify the player adapter the item was removed to update the view
    private fun deletePlayer(id: String) {
        db.collection("players").document(id).delete().addOnSuccessListener {
            val index = players.indexOfFirst { it.id == id }
            if (index != -1) {
                players.removeAt(index)
                this.notifyItemRemoved(index)
            }
        }
    }
}