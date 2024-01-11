package com.oop.lab1.module3

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.oop.lab1.Module3Handler

interface Module3Interface {
    fun run(
        context: Context,
        manager: FragmentManager,
        handler: Module3Handler
    )
}
