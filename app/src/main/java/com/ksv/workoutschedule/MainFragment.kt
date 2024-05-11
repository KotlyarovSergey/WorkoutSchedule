package com.ksv.workoutschedule

//import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ksv.workoutschedule.databinding.FragmentMainBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()

//    private lateinit var repo: Repository
//    private lateinit var workoutPlan: WorkoutPlan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater)
        viewModel.loadSavedData(requireContext())
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        repo = Repository(requireContext())
//        workoutPlan = loadLastWorkoutPlan()
//        workoutPlan.next()
//        initialisation()

//        val toolbar = (requireActivity() as MainActivity).findViewById<Toolbar>(com.ksv.workoutschedule.R.id.toolbar)
//        val menu = toolbar.menu
//        val item = menu.getItem(R.id.menu_history)
//        Log.d("ksvlog", "$item")

//        toolbar.menu.getItem(com.ksv.workoutschedule.R.id.menu_history).isVisible = true

//        (requireActivity() as MainActivity).publicFun()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




    private fun initialisation() {
//        // TODO посторяющиеся операции. Вынести в функию
////        binding.tvPlan.text = currentPlan.currentExercises
//        binding.tvPlan.text = listToNumbericString(workoutPlan.exercises)
//        val number = workoutPlan.press.ordinal + 1
//        val txt = "${getString(R.string.plan_name_prefix)}$number"
//        binding.tvPlanNumber.text = txt
//
//        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
//        binding.tvDate.text = date

//        binding.btnNext.setOnClickListener {
////            binding.tvPlan.text = currentPlan.next()
//
//            binding.tvPlan.text = listToNumbericString(workoutPlan.next())
//            val ordinalNumber = workoutPlan.press.ordinal + 1
//            val text = "${getString(R.string.plan_name_prefix)}$ordinalNumber"
//            binding.tvPlanNumber.text = text
//        }

//        binding.btnBack.setOnClickListener {
////            binding.tvPlan.text = currentPlan.previous()
//            binding.tvPlan.text = listToNumbericString(workoutPlan.previous())
//            val ordinalNumber = workoutPlan.press.ordinal + 1
//            val text = "${getString(R.string.plan_name_prefix)}$ordinalNumber"
//            binding.tvPlanNumber.text = text
//        }

//        binding.btnStart.setOnClickListener {
////            Log.d("ksvlog", "onClick")
////            Toast.makeText(requireContext(), "onClick", Toast.LENGTH_SHORT).show()
////            parentFragmentManager.commit {
////                replace<HistoryFragment>(R.id.fragment_container)
////            }
//
//
//            // может таймер запустить ?????
//
//            binding.btnBreak.visibility = View.VISIBLE
//            binding.btnFinish.visibility = View.VISIBLE
//            binding.btnStart.visibility = View.GONE
//            binding.btnBack.visibility = View.INVISIBLE
//            binding.btnNext.visibility = View.INVISIBLE
////            // TODO сделать переключатель в ресурсах как положено
////            binding.btnBack.setBackgroundColor(getColor(R.color.background))
////            binding.btnNext.setBackgroundColor(getColor(R.color.background))
//        }

//        binding.btnFinish.setOnClickListener {
//            // таймер остановить
//
//            saveData()
//            binding.btnBreak.visibility = View.INVISIBLE
//            binding.btnFinish.visibility = View.INVISIBLE
//            binding.btnStart.visibility = View.VISIBLE
////            binding.tvPlan.text = currentPlan.next()
//            binding.tvPlan.text = listToNumbericString(workoutPlan.next())
//            val ordinalNumber = workoutPlan.press.ordinal + 1
//            val text = "${getString(R.string.plan_name_prefix)}$ordinalNumber"
//            binding.tvPlanNumber.text = text
//            binding.btnBack.visibility = View.VISIBLE
//            binding.btnNext.visibility = View.VISIBLE
////            // TODO сделать переключатель в ресурсах как положено
////            binding.btnBack.setBackgroundColor(getColor(R.color.back_next))
////            binding.btnNext.setBackgroundColor(getColor(R.color.back_next))
//        }

//        binding.btnBreak.setOnClickListener {
//            // таймер остановить
//            binding.btnBreak.visibility = View.INVISIBLE
//            binding.btnFinish.visibility = View.INVISIBLE
//            binding.btnStart.visibility = View.VISIBLE
//            binding.btnBack.visibility = View.VISIBLE
//            binding.btnNext.visibility = View.VISIBLE
////            // TODO сделать переключатель в ресурсах как положено
////            binding.btnBack.setBackgroundColor(getColor(R.color.back_next))
////            binding.btnNext.setBackgroundColor(getColor(R.color.back_next))
//        }
    }

    private fun loadLastWorkoutPlan(): WorkoutPlan {

//        return repo.loadWorkoutPlan()
        return WorkoutPlan(WorkoutPlan.Plans.PressPlan.FIRST, WorkoutPlan.Plans.BarPlan.FIRST)
    }

    private fun saveData() {
//        repo.saveWorkoutPlan(workoutPlan)
//        repo.addWorkoutPlanToHistory(makeTextToHistory())
    }

    private fun makeTextToHistory(): String{
//        val date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd"))
//
//        val builder = StringBuilder()
//        builder.append(date)
//        builder.append(": ")
//        builder.append(workoutPlan.planToHistory())
//        builder.append("\n")
//
//        // TODO добавить время тренировки
//        return builder.toString()

        return ""
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}