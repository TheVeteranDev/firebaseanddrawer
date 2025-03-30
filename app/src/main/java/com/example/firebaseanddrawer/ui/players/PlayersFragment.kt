package com.example.firebaseanddrawer.ui.players

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseanddrawer.R
import com.example.firebaseanddrawer.databinding.FragmentPlayersBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore

class PlayersFragment : Fragment() {

    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!

    // Get the firestore instance to be able to query the database
    private val db = FirebaseFirestore.getInstance()

    // Initialize players array and player adapter
    var players: MutableList<Player> = mutableListOf()
    var playerAdapter: PlayerAdapter = PlayerAdapter(players)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Initialize the recycler view and add the player adapter to it
        val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = playerAdapter

        // Fetch all players from the database
        fetchPlayers()

        // Initialize the add players button
        val addButton: Button = root.findViewById(R.id.add_button)

        // Only setting color programmatically because setting it in the XML was not working
        addButton.setBackgroundColor(Color.parseColor("#002050"))

        // When clicked, opens the player form as a dialog with callback
        // When closed the callback will re-fetch players from the database
        // I realize this maybe inefficient as it uses the callback even if the dialog is canceled
        addButton.setOnClickListener {
            val playerForm = PlayerForm()
            playerForm.show(parentFragmentManager, "playerForm")
            playerForm.onDismissCallback = { fetchPlayers() }
        }

        return root
    }

    // Function that fetches players from the database
    // clears and re-adds players to the array
    // then tells the player adapter there has been a change
    // to refresh the view
    private fun fetchPlayers() {
        db.collection("players").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                players.clear()
                for (doc in task.result!!) {
                    val player = doc.toObject(Player::class.java)
                    player.id = doc.id
                    players.add(player)
                }
                playerAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}