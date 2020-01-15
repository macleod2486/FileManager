/*
 *
 *   File Manager
 *    A application to help organize your files on your device.
 *
 *    Copyright (C) 2019  Manuel Gonzales Jr.
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see [http://www.gnu.org/licenses/].
 *
 */

package com.macleod2486.filemanager.views

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.macleod2486.filemanager.R

class MainActivity : AppCompatActivity()
{
    private lateinit var drawer: DrawerLayout
    private val REQUEST_PERMISSION_CODE = 0

    override fun onBackPressed()
    {
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START)
        }
        else if(supportFragmentManager.backStackEntryCount > 0)
        {
            supportFragmentManager.popBackStack()
        }
        else
        {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart()
    {
        super.onStart()

        if(Build.VERSION.SDK_INT >= 23)
        {
            val writeExternalPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            val readExternalPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED

            if (writeExternalPermission || readExternalPermission)
            {
                val arrayOfPermissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                this.requestPermissions(arrayOfPermissions, REQUEST_PERMISSION_CODE)
            }
            else
            {
                setupMainView()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_PERMISSION_CODE)
        {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                setupMainView()
            }
            else
            {
                finish()
            }
        }
    }

    private fun setupMainView()
    {
        val mainFragment = Main()

        supportFragmentManager.beginTransaction().replace(R.id.container, mainFragment).commit()

        drawer = findViewById(R.id.drawer)

        val drawerOptions = findViewById<ListView>(R.id.optionList)
        val menuItems = resources.getStringArray(R.array.menuItems)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, menuItems)
        drawerOptions.setAdapter(adapter)
    }
}
