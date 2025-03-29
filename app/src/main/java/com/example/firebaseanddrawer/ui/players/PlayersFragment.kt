package com.example.firebaseanddrawer.ui.players

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseanddrawer.R
import com.example.firebaseanddrawer.databinding.FragmentPlayersBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore

class PlayersFragment : Fragment() {

    private var _binding: FragmentPlayersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val players: MutableList<Player> = mutableListOf()

        db.collection("players").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (doc in task.result!!) {
                    val player = doc.toObject(Player::class.java)
                    players.add(player)

                    Log.d("Player Data", "Player: ${player.name}, ${player.number}, ${player.position}, ${player.status}, ${player.photo}")
                }

                val recyclerView: RecyclerView = root.findViewById(R.id.recycler_view)
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                recyclerView.adapter = PlayerAdapter(players)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}