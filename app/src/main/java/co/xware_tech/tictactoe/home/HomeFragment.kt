package co.xware_tech.tictactoe.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import co.xware_tech.tictactoe.R
import co.xware_tech.tictactoe.databinding.FragmentHomeBinding
import co.xware_tech.tictactoe.gamefragment.GameFragment

class HomeFragment:Fragment(R.layout.fragment_home) {
    private lateinit var binding : FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentHomeBinding.inflate(inflater,container,false)
            .also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStartGame.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGameFragment())
        }
    }
}