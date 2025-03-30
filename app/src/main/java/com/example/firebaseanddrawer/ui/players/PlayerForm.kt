package com.example.firebaseanddrawer.ui.players

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.firebaseanddrawer.R
import com.example.firebaseanddrawer.databinding.FragmentPlayerformBinding
import com.google.firebase.firestore.FirebaseFirestore

class PlayerForm : DialogFragment() {

    // Create a callback that allows us to fetch players after the dialog is closed
    var onDismissCallback: (() -> Unit)? = null

    private var _binding: FragmentPlayerformBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerformBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Get an instance of the firestore database
        val db = FirebaseFirestore.getInstance()

        // Initialize the submit button
        val submitButton: Button = root.findViewById(R.id.submit_button)

        // Only setting color programmatically because setting it in the XML was not working
        submitButton.setBackgroundColor(Color.parseColor("#002050"))

        // Set on click listener that gets all the values of the form and tries to save
        // it to the database
        submitButton.setOnClickListener {
            val player: Player = Player(
                "",
                binding.nameInput.text.toString(),
                binding.numberInput.text.toString().toInt(),
                binding.positionInput.text.toString(),
                binding.statusInput.text.toString(),
                "no_player_photo"  // Did not add a way for users to add an image so this is default
            )

            // Attempt to save the new player the database, on success close the dialog
            db.collection("players").add(player).addOnSuccessListener {
                dismiss()
            }
        }

        // Initalize the cancel button
        val cancelButton: Button = root.findViewById(R.id.cancel_button)

        // Only setting color programmatically because setting it in the XML was not working
        cancelButton.setBackgroundColor(Color.parseColor("#FFFFFF"))
        cancelButton.setTextColor(Color.parseColor("#002050"))

        // If clicked close the dialog
        cancelButton.setOnClickListener {
            dismiss()
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissCallback?.invoke() // Invoke the callback on dismiss
    }
}