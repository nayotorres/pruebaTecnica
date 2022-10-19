package com.torres.pruebatecnica.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.torres.pruebatecnica.databinding.FragmentDetailBinding
import com.torres.pruebatecnica.util.Constants
import com.torres.pruebatecnica.util.CustomProgress
import com.torres.pruebatecnica.util.Utils
import com.torres.pruebatecnica.util.extension.showGenericAlertDialog
import com.torres.pruebatecnica.viewmodel.DetailViewModel
import com.torres.pruebatecnica.vo.Resource
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailFragment : Fragment() {

    private lateinit var binding : FragmentDetailBinding
    var progressBar: CustomProgress = CustomProgress()
    private val viewModel: DetailViewModel by viewModel()
    var movieId:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            movieId = it.getInt("movieId",0)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        viewModel.setId(movieId)

        binding.toolbar.setNavigationOnClickListener {
            NavHostFragment.findNavController(requireParentFragment()).popBackStack()
        }
    }

    fun setupObserver(){

        viewModel.movie.observe(viewLifecycleOwner, Observer {
            when(it){

                is Resource.Loading->{
                        progressBar.show(requireContext(),false)
                }

                is Resource.Success->{

                    progressBar.dialog!!.dismiss()
                    Glide.with(requireContext())
                        .asBitmap()
                        .load(Constants.URL_IMG + it.data!!.poster_path)
                        .into(binding.imgMovie)

                    binding.tvLenguage.text = it.data!!.original_language
                    binding.tvAverage.text = "${it.data!!.vote_average}"
                    binding.tvTitle.text = it.data!!.title
                    binding.tvDescription.text = it.data!!.overview
                    binding.tvAnio.text = Utils.formatedDate(it.data!!.release_date!!)

                    val idVideo = it.data.id!!

                    binding.btOpenVideo.setOnClickListener {
                        viewModel.setVideoId(idVideo)
                    }
                }

                is Resource.DataError->{
                    progressBar.dialog!!.dismiss()
                    requireContext().showGenericAlertDialog(it.errorMessage!!)
                }
            }
        })

        viewModel.video.observe(viewLifecycleOwner,Observer{
            when(it){

                is Resource.Loading->{
                    progressBar.show(requireContext(),false)
                }

                is Resource.Success->{
                    progressBar.dialog!!.dismiss()

                    if(it.data!!.results!!.isNotEmpty()){
                        val intent = Intent(requireActivity(), VideoActivity::class.java)
                        intent.putExtra("videoId",it.data!!.results!![0].key)
                        startActivity(intent)
                    }
                }

                is Resource.DataError->{
                    requireContext().showGenericAlertDialog(it.errorMessage!!)
                }
            }
        })
    }

}