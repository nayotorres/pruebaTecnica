package com.torres.pruebatecnica.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.torres.pruebatecnica.R
import com.torres.pruebatecnica.adapter.MovieAdapter
import com.torres.pruebatecnica.data.model.MovieDao
import com.torres.pruebatecnica.databinding.FragmentHomeBinding
import com.torres.pruebatecnica.util.CustomProgress
import com.torres.pruebatecnica.util.OnClickAdapter
import com.torres.pruebatecnica.util.Utils
import com.torres.pruebatecnica.viewmodel.HomeViewModel
import com.torres.pruebatecnica.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding:FragmentHomeBinding
    var progressBar: CustomProgress = CustomProgress()

    private lateinit var adapterTop:MovieAdapter
    private lateinit var adapterUpcoming:MovieAdapter
    private lateinit var adapterSuggestions:MovieAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        setupObserver()
        setupSwitch()

        if(Utils.isConnect(requireContext())){
            viewModel.setUpcoming("b")
            viewModel.setTopRated("a")
        }else{
            viewModel.setMovieBD("c")
        }
    }

    fun setupObserver(){

        viewModel.upcoming.observe(viewLifecycleOwner, Observer {
            when(it){

                is Resource.Loading->{
                    progressBar.show(requireContext())
                }

                is Resource.Success->{
                    progressBar.dialog!!.dismiss()
                    if(it.data!!.results!!.isNotEmpty()){
                        val listMov = ArrayList<MovieDao>()
                        for (mov in it.data!!.results!! ){
                            val movie = MovieDao(mov.id!!,mov.title!!,mov.overview!!,mov.poster_path?:"",mov.release_date!!,mov.original_language!!,1)
                            listMov.add(movie)
                            viewModel.saveMovie(movie)
                        }

                        setupUpcoming(listMov)

                    }

                }

                is Resource.DataError->{
                    progressBar.dialog!!.dismiss()
                }
            }
        })

        viewModel.topRated.observe(viewLifecycleOwner, Observer {
            when(it){

                is Resource.Loading->{

                }

                is Resource.Success->{
                    if(it.data!!.results!!.isNotEmpty()){
                        val listMov = ArrayList<MovieDao>()
                        for (mov in it.data!!.results!! ){
                            val movie = MovieDao(mov.id!!,mov.title!!,mov.overview!!,mov.poster_path?:"",mov.release_date!!,mov.original_language!!,2)
                            listMov.add(movie)
                            viewModel.saveMovie(movie)
                        }
                        setupTop(listMov)

                    }
                }

                is Resource.DataError->{

                }
            }
        })

        viewModel.movieDB.observe(viewLifecycleOwner,Observer{
            when(it){

                is Resource.Success->{
                    val upcoming = it.data!!.filter { it.category==1 }
                    setupUpcoming(upcoming as ArrayList<MovieDao>)
                    val top = it.data!!.filter { it.category==2 }
                    setupTop(top as ArrayList<MovieDao>)

                }
            }
        })


    }

    fun setupRecycler(){

        binding.rvTopRated.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvUpcoming.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.rvSuggestions.layoutManager = GridLayoutManager(requireContext(),2)

    }

    fun setupSwitch(){
        binding.tvIdioma.setOnClickListener {

            binding.tvIdioma.tag?.let {
                val items = it as ArrayList<MovieDao>
                setupData(items)
            }

            binding.tvIdioma.background =
                ResourcesCompat.getDrawable(resources, R.drawable.switch_trcks, null)
            binding.tvIdioma.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.black
                )
            )
            binding.tvAnio.background = null
            binding.tvAnio.setTextColor( ContextCompat.getColor( requireContext(), R.color.white))

        }

        binding.tvAnio.setOnClickListener {

            binding.tvAnio.tag?.let {
                val items = it as ArrayList<MovieDao>
                setupData(items)
            }
            binding.tvIdioma.background = null
            binding.tvIdioma.setTextColor(ContextCompat.getColor( requireContext(), R.color.white))

            binding.tvAnio.background = ResourcesCompat.getDrawable(resources, R.drawable.switch_trcks, null)
            binding.tvAnio.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))

        }

    }

    fun setupData(items:ArrayList<MovieDao>){

        adapterSuggestions = MovieAdapter(requireContext(),items,object :OnClickAdapter{
            override fun onClick(result: MovieDao) {
                openDetail(result.uuidMovie!!)
            }
        })

        binding.rvSuggestions.apply {
            adapter = adapterSuggestions
        }
    }

    fun setupTop(items:ArrayList<MovieDao>){
        adapterTop = MovieAdapter(requireContext(),items,object:OnClickAdapter{
            override fun onClick(result: MovieDao) {
                openDetail(result.uuidMovie!!)
            }
        })

        binding.rvTopRated.apply {
            adapter = adapterTop
        }

        val filterIdioma = items.filter { it.originalLanguage=="es" }
        val filterAnio = items.filter { it.releaseDate.contains("2016") }

        setupData(filterIdioma as ArrayList<MovieDao>)

        binding.tvAnio.tag = filterAnio as ArrayList<MovieDao>
        binding.tvIdioma.tag = filterIdioma as ArrayList<MovieDao>
    }

    fun setupUpcoming(items:ArrayList<MovieDao>){
        adapterUpcoming = MovieAdapter(requireContext(),items,object:OnClickAdapter{
            override fun onClick(result: MovieDao) {
                openDetail(result.uuidMovie!!)
            }
        })

        binding.rvUpcoming.apply {
            adapter = adapterUpcoming
        }
    }

    fun openDetail(movieId:Int){
        val bundle = Bundle()
        bundle.putInt("movieId",movieId)
        NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

}