package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eqn1 = binding.Equation1
        val eqn2 = binding.Equation2
        val add = binding.add
        val mul = binding.mult
        val div = binding.divide
        val sub = binding.sub
        val res = binding.Result

        add.setOnClickListener{

            val num1 = eqn1.text.toString().toInt()
            val num2 = eqn2.text.toString().toInt()
            val result = num1+num2

            res.text = result.toString()
        }

        sub.setOnClickListener{
            val num1 = eqn1.text.toString().toInt()
            val num2 = eqn2.text.toString().toInt()
            val result = num1-num2

            res.text = result.toString()
        }

        mul.setOnClickListener{
            val num1 = eqn1.text.toString().toInt()
            val num2 = eqn2.text.toString().toInt()
            val result = num1*num2

            res.text = result.toString()
        }

        div.setOnClickListener{
            val num1 = eqn1.text.toString().toDouble()
            val num2 = eqn2.text.toString().toDouble()
            val result = num1/num2

            res.text = result.toString()
        }

    }
}