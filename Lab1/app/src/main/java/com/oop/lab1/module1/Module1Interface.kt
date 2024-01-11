package com.oop.lab1.module1

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.oop.lab1.Module1Handler

interface Module1Interface {
    fun run(
        context: Context,
        manager: FragmentManager,
        handler: Module1Handler
    )
}
