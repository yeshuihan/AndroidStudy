package com.yeshuihan.androidstudy

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

object Test {
    fun  test() {
        println("test")

        runBlocking {

        }



        GlobalScope.launch {

        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        runBlocking {
            System.out.println(this)
        }

        GlobalScope.launch {
            println(this)
        }
    }
}