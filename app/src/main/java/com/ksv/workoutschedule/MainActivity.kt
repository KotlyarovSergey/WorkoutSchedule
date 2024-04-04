package com.ksv.workoutschedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ksv.workoutschedule.databinding.ActivityMainBinding
import java.lang.StringBuilder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var workoutPlan: WorkoutPlan
    private var timer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        workoutPlan = loadData()
        initialisation()

    }


    private fun initialisation() {
        // TODO посторяющиеся операции. Вынести в функию
//        binding.tvPlan.text = currentPlan.currentExercises
        binding.tvPlan.text = listToNumbericString(workoutPlan.currentExercises)
        val number = workoutPlan.currentPress.ordinal + 1
        val txt = "${getString(R.string.plan_name_prefix)}$number"
        binding.tvPlanNumber.text = txt

        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        binding.tvDate.text = date

        binding.btnNext.setOnClickListener {
//            binding.tvPlan.text = currentPlan.next()
            binding.tvPlan.text = listToNumbericString(workoutPlan.next())
            val number = workoutPlan.currentPress.ordinal + 1
            val txt = "${getString(R.string.plan_name_prefix)}$number"
            binding.tvPlanNumber.text = txt
        }

        binding.btnBack.setOnClickListener {
//            binding.tvPlan.text = currentPlan.previous()
            binding.tvPlan.text = listToNumbericString(workoutPlan.previous())
            val number = workoutPlan.currentPress.ordinal + 1
            val txt = "${getString(R.string.plan_name_prefix)}$number"
            binding.tvPlanNumber.text = txt
        }

        binding.btnStart.setOnClickListener {
            // может таймер запустить ?????

            binding.btnBreak.visibility = View.VISIBLE
            binding.btnFinish.visibility = View.VISIBLE
            binding.btnStart.visibility = View.GONE
            binding.btnBack.visibility = View.INVISIBLE
            binding.btnNext.visibility = View.INVISIBLE
//            // TODO сделать переключатель в ресурсах как положено
//            binding.btnBack.setBackgroundColor(getColor(R.color.background))
//            binding.btnNext.setBackgroundColor(getColor(R.color.background))
        }

        binding.btnFinish.setOnClickListener {
            // таймер остановить

            saveData()
            binding.btnBreak.visibility = View.INVISIBLE
            binding.btnFinish.visibility = View.INVISIBLE
            binding.btnStart.visibility = View.VISIBLE
//            binding.tvPlan.text = currentPlan.next()
            binding.tvPlan.text = listToNumbericString(workoutPlan.next())
            val number = workoutPlan.currentPress.ordinal + 1
            val txt = "${getString(R.string.plan_name_prefix)}$number"
            binding.tvPlanNumber.text = txt
            binding.btnBack.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE
//            // TODO сделать переключатель в ресурсах как положено
//            binding.btnBack.setBackgroundColor(getColor(R.color.back_next))
//            binding.btnNext.setBackgroundColor(getColor(R.color.back_next))
        }

        binding.btnBreak.setOnClickListener {
            // таймер остановить
            binding.btnBreak.visibility = View.INVISIBLE
            binding.btnFinish.visibility = View.INVISIBLE
            binding.btnStart.visibility = View.VISIBLE
            binding.btnBack.visibility = View.VISIBLE
            binding.btnNext.visibility = View.VISIBLE
//            // TODO сделать переключатель в ресурсах как положено
//            binding.btnBack.setBackgroundColor(getColor(R.color.back_next))
//            binding.btnNext.setBackgroundColor(getColor(R.color.back_next))
        }
    }

    private fun loadData(): WorkoutPlan {


        return WorkoutPlan(
            WorkoutPlan.PressExercises.PressPlans.FIRST,
            WorkoutPlan.PressExercises.BarPlans.FIRST
        )
    }

    private fun saveData() {
        val press = workoutPlan.currentPress.name
        val bar = workoutPlan.currentBar.name


        Log.i("ksvlog", "press: $press, bar: $bar")
        try {
            var p = WorkoutPlan.PressExercises.PressPlans.valueOf(press)  // Здесь вылет если завершаем THIRD план !!!!!!!!!!!
            Log.i("ksvlog", "p: ${p.name}")
        } catch (exception: IllegalArgumentException){
            // как-то так может случиться, что поля с таким именем в enum'е нет
        }


    }

    private fun listToNumbericString(list: List<String>): String {
        val builder = StringBuilder()
        for ((i, e) in list.withIndex()) {
            builder.append("${i + 1}. $e\n")
        }
        if (builder.isNotEmpty())
            builder.setLength(builder.length - 1)
        return builder.toString()

    }
}