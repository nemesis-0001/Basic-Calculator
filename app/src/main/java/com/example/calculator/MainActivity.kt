package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var canAddOperation = false
    private var canAddDecimal = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun allClearAction(view:View){
        binding.resultTV.text = ""
        binding.workingTV.text = ""
    }

    fun onBackAction(view:View){
        val length = binding.workingTV.length()
        if(length>0)
           binding.workingTV.text= binding.workingTV.text.subSequence(0,length-1)
    }

    fun onEqualAction(view:View){
        binding.resultTV.visibility = View.VISIBLE
        binding.resultTV.text = calculateResults()
    }

    private fun calculateResults(): String {
        val digitsOperator = digitsoperators()
        if(digitsOperator.isEmpty()) return ""

        val timesDivision = timesDivisionCalculate(digitsOperator)
        if(timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        return result.toString()
    }


    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList
        while(list.contains('X') || list.contains('/') || list.contains('%'))
            list = calcTimesDiv(list)

        return list
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float
        for(i in passedList.indices){
            if(passedList[i] is Char && i!=passedList.lastIndex){
                val operator = passedList[i]
                val nextDigit = passedList[i+1] as Float
                if(operator=='+')
                    result += nextDigit
                if(operator=='-')
                    result -= nextDigit
            }
        }
        return result
    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any> {

        val newList = mutableListOf<Any>()

        var restartIndex = passedList.size

        for(i in passedList.indices){
            if(passedList[i] is Char && i!=passedList.lastIndex && i<restartIndex){
                val operator = passedList[i]
                val prevDigit =  passedList[i-1] as Float
                val nextDigit =  passedList[i+1] as Float

                when(operator){
                    'X'->{
                        newList.add((prevDigit*nextDigit))
                        restartIndex = i+1
                    }
                    '/'->{
                        newList.add((prevDigit/nextDigit))
                        restartIndex = i+1
                    }
                    '%'->{
                        newList.add((prevDigit%nextDigit))
                        restartIndex = i+1
                    }
                    else->{
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }
            if(i>restartIndex)
                newList.add(passedList)
        }

        return newList
    }

    private fun digitsoperators():MutableList<Any>{
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for(character in binding.workingTV.text){
            if(character.isDigit() || character == '.')
                currentDigit += character
            else{
                list.add(currentDigit.toFloat())
                currentDigit=""
                list.add(character)
            }
        }
        if(currentDigit!=""){
            list.add(currentDigit.toFloat())
        }
        return list
    }

    fun onNumberAction(view: View){
        if(view is Button){
            if(view.text == "."){
                if(canAddDecimal){
                    binding.workingTV.append(view.text)
                }
                canAddDecimal = false
            }
            else
                binding.workingTV.append(view.text)
            canAddOperation = true
        }
    }

    fun onOperationAction(view:View){
        if(view is Button && canAddOperation){
            binding.workingTV.append(view.text)
            canAddOperation = false
        }
    }
}