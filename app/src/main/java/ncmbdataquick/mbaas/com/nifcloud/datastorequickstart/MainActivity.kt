package ncmbdataquick.mbaas.com.nifcloud.datastorequickstart

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.nifcloud.mbaas.core.NCMB
import com.nifcloud.mbaas.core.NCMBCallback
import com.nifcloud.mbaas.core.NCMBException
import com.nifcloud.mbaas.core.NCMBObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //**************** APIキーの設定とSDKの初期化 **********************
        NCMB.initialize(this, "YOUR_APPLICATION_KEY", "YOUR_CLIENT_KEY")
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
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    fun doStartDemo(view: View) {
        val obj = NCMBObject("TestClass")
        try {
            obj.put("message", "Hello, NCMB!")
            obj.saveInBackground(NCMBCallback { e, ncmbObj ->
                if (e != null) {
                    //保存失敗
                    Handler(Looper.getMainLooper()).post {
                        AlertDialog.Builder(this)
                            .setTitle("Notification from NIFCLOUD")
                            .setMessage("Error:" + e.message)
                            .setPositiveButton("OK", null)
                            .show()
                    }
                } else {
                    Handler(Looper.getMainLooper()).post {
                        //保存成功
                        val result = ncmbObj as NCMBObject
                        AlertDialog.Builder(this)
                            .setTitle("Notification from NIFCLOUD")
                            .setMessage("Save successfull! with ID:" + result.getObjectId())
                            .setPositiveButton("OK", null)
                            .show()
                    }

                }
            })
        } catch (e: NCMBException) {
            e.printStackTrace()
        }

    }
}
