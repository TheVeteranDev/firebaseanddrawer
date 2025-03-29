package com.example.firebaseanddrawer.ui.players

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseanddrawer.R

class PlayerAdapter(
    private val players: MutableList<Player>
) : RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder>()  {

    class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.player_name)
        private val numberTextView: TextView = itemView.findViewById(R.id.player_number)
        private val positionTextView: TextView = itemView.findViewById(R.id.player_position)
        private val statusTextView: TextView = itemView.findViewById(R.id.player_status)
        private val photoImageView: ImageView = itemView.findViewById(R.id.player_photo)

        fun bind(player: Player) {
            nameTextView.text = player.name
            numberTextView.text = player.number.toString()
            positionTextView.text = player.position
            statusTextView.text = player.status
            photoImageView.setImageResource(player.photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_player, parent, false)
        return PlayerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        val player = players[position]
        holder.bind(player)
    }

    override fun getItemCount(): Int {
        return players.size
    }

}