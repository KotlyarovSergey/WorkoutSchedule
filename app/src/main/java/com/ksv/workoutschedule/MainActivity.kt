package com.ksv.workoutschedule

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.ksv.workoutschedule.databinding.ActivityMainBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.text.StringBuilder

class MainActivity : AppCompatActivity() {
    // TODO
    // сохранине/загрузка последней тренировки
    // Таймер
    // истроия тренировок
    //


    private lateinit var binding: ActivityMainBinding
    private lateinit var workoutPlan: WorkoutPlan
    private lateinit var repo: Repository
    private var timer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repo = Repository(this)
        workoutPlan = loadLastWorkoutPlan()
        workoutPlan.next()
        initialisation()


        // todo удалить! использовалось для просмотра истории
        binding.tvPlan.setOnClickListener {
            val history = repo.loadHistory()
            binding.tvPlan.textSize = 18.0f
//            val text = history.toString()
            val text = history.joinToString("\n")
            binding.tvPlan.text = text
        }
    }


    private fun initialisation() {
        // TODO посторяющиеся операции. Вынести в функию
//        binding.tvPlan.text = currentPlan.currentExercises
        binding.tvPlan.text = listToNumbericString(workoutPlan.exercises)
        val number = workoutPlan.press.ordinal + 1
        val txt = "${getString(R.string.plan_name_prefix)}$number"
        binding.tvPlanNumber.text = txt

        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        binding.tvDate.text = date

        binding.btnNext.setOnClickListener {
//            binding.tvPlan.text = currentPlan.next()

            binding.tvPlan.text = listToNumbericString(workoutPlan.next())
            val ordinalNumber = workoutPlan.press.ordinal + 1
            val text = "${getString(R.string.plan_name_prefix)}$ordinalNumber"
            binding.tvPlanNumber.text = text
        }

        binding.btnBack.setOnClickListener {
//            binding.tvPlan.text = currentPlan.previous()
            binding.tvPlan.text = listToNumbericString(workoutPlan.previous())
            val ordinalNumber = workoutPlan.press.ordinal + 1
            val text = "${getString(R.string.plan_name_prefix)}$ordinalNumber"
            binding.tvPlanNumber.text = text
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
            val ordinalNumber = workoutPlan.press.ordinal + 1
            val text = "${getString(R.string.plan_name_prefix)}$ordinalNumber"
            binding.tvPlanNumber.text = text
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

    private fun loadLastWorkoutPlan(): WorkoutPlan {

        return repo.loadWorkoutPlan()
    }

    private fun saveData() {
        repo.saveWorkoutPlan(workoutPlan)
        repo.addWorkoutPlanToHistory(getLineToHistory())
    }

    private fun getLineToHistory(): String{
        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

        val builder = StringBuilder()
        builder.append(date)
        builder.append(": ")
        builder.append(workoutPlan.planToHistory())
        builder.append("\n")

        return builder.toString()
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