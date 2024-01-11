package com.oop.lab1.module2

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.oop.lab1.Module2Handler

interface Module2Interface {
    fun run(
        context: Context,
        manager: FragmentManager,
        handler: Module2Handler
    )
}
