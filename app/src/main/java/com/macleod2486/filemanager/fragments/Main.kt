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

package com.macleod2486.filemanager.fragments

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import com.macleod2486.filemanager.R

class Main : Fragment()
{
    private lateinit var gridLayout:GridLayout

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.main_fragment, container, false)

        gridLayout = view.findViewById(R.id.iconLayout)

        return view
    }

    override fun onStart()
    {
        super.onStart()

        var button: Button

        var listOfFiles = Environment.getRootDirectory().listFiles()

        var x = 0
        var y = 0

        for(file in listOfFiles)
        {
            button = Button(activity?.applicationContext)
            button.setText(file.absolutePath)

            val params = GridLayout.LayoutParams()

            if(x < 2)
            {
                params.rowSpec = GridLayout.spec(y)
                params.columnSpec = GridLayout.spec(x)
                x++
            }
            else
            {
                x = 0
                y++
                params.rowSpec = GridLayout.spec(y)
                params.columnSpec = GridLayout.spec(x)
                x++
            }

            button.layoutParams = params

            gridLayout.addView(button)
        }

    }
}