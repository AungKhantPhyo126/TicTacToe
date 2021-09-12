package co.xware_tech.tictactoe.gamefragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import co.xware_tech.tictactoe.R
import co.xware_tech.tictactoe.databinding.FragmentGameBinding
import co.xware_tech.tictactoe.databinding.FragmentHomeBinding
import com.google.android.material.button.MaterialButton

class GameFragment : Fragment() {
    private lateinit var binding: FragmentGameBinding
    private lateinit var availablePlaces: MutableList<MaterialButton>
    private val viewmodel by viewModels<GameViewModel>()
    private var roundCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentGameBinding.inflate(inflater, container, false)
            .also { binding = it }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        availablePlaces = mutableListOf(
            binding.card00, binding.card01, binding.card02,
            binding.card10, binding.card11, binding.card12,
            binding.card20, binding.card21, binding.card22
        )
        click(binding.card00)
        click(binding.card01)
        click(binding.card02)
        click(binding.card10)
        click(binding.card11)
        click(binding.card12)
        click(binding.card20)
        click(binding.card21)
        click(binding.card22)

        viewmodel.userTurn.observe(viewLifecycleOwner, {
            if (it == false) {
                //disable touch
                for (i in availablePlaces) {
                    i.isEnabled = false
                }

                if (availablePlaces.size == 0) {
                    binding.gpContainer.visibility = View.INVISIBLE
                    binding.tvResult.isVisible = true
                    binding.tvResult.text = "Draw"
                }
                Handler().postDelayed({
                    botplay()
                }, 1000)
            } else {
                //enable touch
                for (i in availablePlaces) {
                    i.isEnabled = true
                }
            }
        })

        binding.btnRestart.setOnClickListener {
            availablePlaces.clear()
            binding.card00.text = ""
            binding.card01.text = ""
            binding.card02.text = ""
            binding.card10.text = ""
            binding.card11.text = ""
            binding.card12.text = ""
            binding.card20.text = ""
            binding.card21.text = ""
            binding.card22.text = ""
            var available = mutableListOf("00", "01", "02", "10", "11", "12", "20", "21", "22")
            availablePlaces = mutableListOf(
                binding.card00, binding.card01, binding.card02,
                binding.card10, binding.card11, binding.card12,
                binding.card20, binding.card21, binding.card22
            )
            viewmodel.resetTurn()

            binding.gpContainer.visibility = View.VISIBLE
            binding.tvResult.visibility = View.INVISIBLE
        }

    }

    private fun click(view: MaterialButton) {
            viewmodel.userTurn.observe(viewLifecycleOwner, {userTurn->
                view.setOnClickListener {
                    if (userTurn) {
                        //user play
                        val string = availablePlaces[0].text
                        if (availablePlaces.contains(view)) {
                            val v = view.id
                            view.text = "0"
                            if (checkWinner()) {
                                binding.gpContainer.isVisible = false
                                binding.tvResult.isVisible = true
                                binding.tvResult.text = "You Win"
                                viewmodel.resetTurn()
                            } else {
                                availablePlaces.remove(view)
                                viewmodel.changeTurn()
                            }
                        }
                    } else {
                        //bot play
                        if (availablePlaces.contains(view)) {
                            view.text = "x"
                            if (checkWinner()) {
                                binding.gpContainer.isVisible = false
                                binding.tvResult.isVisible = true
                                binding.tvResult.text = "Bot Win"
                                viewmodel.resetTurn()
                            } else {
                                availablePlaces.remove(view)
                                viewmodel.changeTurn()
                            }
                        }
                    }
                }

            })

    }

    private fun botplay() {
        availablePlaces[viewmodel.randomNumber(availablePlaces.size - 1)].performClick()
    }

    private fun checkWinner(): Boolean {
        //Horizontal
        return if (binding.card00.text == binding.card01.text && binding.card00.text == binding.card02.text && binding.card00.text!="") {
            true
        } else if (binding.card10.text == binding.card11.text && binding.card10.text == binding.card12.text && binding.card10.text!="") {
            true
        } else if (binding.card20.text == binding.card21.text && binding.card20.text == binding.card22.text && binding.card20.text!="") {
            true
        }

        //Vertical
        else if (binding.card00.text == binding.card10.text && binding.card00.text == binding.card20.text && binding.card00.text!="") {
            true
        } else if (binding.card01.text == binding.card11.text && binding.card01.text == binding.card21.text && binding.card01.text!="") {
            true
        } else if (binding.card02.text == binding.card12.text && binding.card02.text == binding.card22.text && binding.card02.text!="") {
            true
        }

        //Diagonal
        else if (binding.card00.text == binding.card11.text && binding.card00.text == binding.card22.text && binding.card00.text!="") {
            true
        } else binding.card02.text == binding.card11.text && binding.card02.text == binding.card20.text && binding.card02.text!=""

    }
}