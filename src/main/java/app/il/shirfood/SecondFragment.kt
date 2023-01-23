package app.il.shirfood

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import app.il.shirfood.databinding.FragmentSecondBinding
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    lateinit var mySharedPreference: SharedPreferences

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = getView()?.findViewById<ListView>(R.id.listView_historyView)
        var listViewHistoryView:Array<String> = arrayOf()

        mySharedPreference =
            activity?.getSharedPreferences(getString(R.string.serialNumber), Context.MODE_PRIVATE)!!
        var count: String? = mySharedPreference.getString(getString(R.string.serialNumber), "0")
        var countInt = count?.toInt()

        for (i in countInt!! downTo 1) {
            mySharedPreference = activity?.getSharedPreferences(i.toString(), Context.MODE_PRIVATE)!!
            var toAdd: String? = mySharedPreference.getString(i.toString(), "נכשל, יש לפנות למתכנת")
            listViewHistoryView+=toAdd.toString()
        }

        val myListAdapter = MyListAdapter(requireActivity(),listViewHistoryView,listViewHistoryView)
        if (listView != null) {
            listView.adapter = myListAdapter
        }

        /*val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_activated_1, listViewHistoryView)
        if (listView != null) {
            listView.adapter = arrayAdapter
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}