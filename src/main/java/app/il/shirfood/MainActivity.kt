package app.il.shirfood

import android.app.TimePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.TimePicker
import android.widget.Toast
import app.il.shirfood.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->

            val currentTimeString = SimpleDateFormat("HH:mm").format(Date())

            var mySharedPreference:SharedPreferences = getSharedPreferences(getString(R.string.lastMeal),
                MODE_PRIVATE)
            var editor: SharedPreferences.Editor = mySharedPreference.edit()
            editor.putString(getString(R.string.lastMeal), currentTimeString.toString())
            editor.apply()

            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
            val currentDate = sdf.format(Date())
            val serialNumberInt = serialNumber().toString()


            mySharedPreference = getSharedPreferences(serialNumberInt, MODE_PRIVATE)
            editor = mySharedPreference.edit()
            editor.putString(serialNumberInt,currentDate)
            editor.apply()

            Toast.makeText(applicationContext, "עודכן ששיר אכלה...", Toast.LENGTH_SHORT).show()
            recreate()

            /*Snackbar.make(view, "", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()*/
        }

        binding.fab2.setOnClickListener { view ->

            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ TimePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY,hour)
                cal.set(Calendar.MINUTE, minute)

                // add to first view
                var currentTimeString = SimpleDateFormat("HH:mm").format(cal.time)
                var mySharedPreference:SharedPreferences = getSharedPreferences(getString(R.string.lastMeal),
                    MODE_PRIVATE)
                var editor: SharedPreferences.Editor = mySharedPreference.edit()
                editor.putString(getString(R.string.lastMeal), currentTimeString.toString())
                editor.apply()

                // add to history
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val currentDate = sdf.format(cal.time)
                val serialNumberInt = serialNumber().toString()
                mySharedPreference = getSharedPreferences(serialNumberInt, MODE_PRIVATE)
                editor = mySharedPreference.edit()
                editor.putString(serialNumberInt,currentDate)
                editor.apply()

                // update user
                Toast.makeText(applicationContext, "עודכן ידנית ששיר אכלה...", Toast.LENGTH_SHORT).show()
                recreate()
            }

            TimePickerDialog(this,timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun serialNumber(): Int? {
        var mySharedPreference: SharedPreferences = getSharedPreferences(
            getString(R.string.serialNumber),
            MODE_PRIVATE
        )
        var serialNumberString: String? = mySharedPreference.getString(getString(R.string.serialNumber), "0")
        var serialNumberInt: Int? = serialNumberString?.toInt()

        if (serialNumberInt != null)
            serialNumberInt += 1

        var editor: SharedPreferences.Editor = mySharedPreference.edit()
        editor.putString(getString(R.string.serialNumber),serialNumberInt.toString() )
        editor.apply()

        return serialNumberInt
    }
}