package app.il.shirfood

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.il.shirfood.databinding.FragmentFirstBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    lateinit var mySharedPreference: SharedPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mySharedPreference =
            activity?.getSharedPreferences(getString(R.string.lastMeal), MODE_PRIVATE)!!
        var timeLastMeal: String? = mySharedPreference.getString(getString(R.string.lastMeal), "")

        if(timeLastMeal?.contains(":") == true) {
            binding.textviewLastMealTime.text = timeLastMeal

            // calculate time to eat
            var splited = timeLastMeal.split(":")
            var timeToEat = (splited[0].toInt())+3
            if(timeToEat > 24) timeToEat-=24
            if(timeToEat == 24) timeToEat =0

            // Visualization of the time she should eat
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY,timeToEat)
            cal.set(Calendar.MINUTE, splited[1].toInt())
            val sdf = SimpleDateFormat("HH:mm").format(cal.time)
            binding.textviewNextMealTime.text = sdf
        }
        else {
            binding.textviewLastMealTime.text = "--:--"
            binding.textviewNextMealTime.text = "--:--"
        }

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}