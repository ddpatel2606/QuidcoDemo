package com.dixitpatel.quidcodemo.ui.tracks

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dixitpatel.quidcodemo.R
import com.dixitpatel.quidcodemo.constant.USER_GET_TOP_TRACKS
import com.dixitpatel.quidcodemo.databinding.FragmentMainBinding
import com.dixitpatel.quidcodemo.databinding.ItemLoadMoreBinding
import com.dixitpatel.quidcodemo.databinding.RowItemAllBinding
import com.dixitpatel.quidcodemo.network.ApiInterface
import com.dixitpatel.quidcodemo.network.AuthStatus
import com.dixitpatel.quidcodemo.prefs.PrefEntity
import com.dixitpatel.quidcodemo.prefs.Preferences
import com.dixitpatel.quidcodemo.ui.base.BaseFragment
import com.dixitpatel.quidcodemo.ui.detail.DetailViewActivity
import com.dixitpatel.quidcodemo.ui.tracks.model.Track
import com.dixitpatel.quidcodemo.utils.Alerts
import com.dixitpatel.quidcodemo.utils.CommonAdapter
import com.dixitpatel.quidcodemo.utils.Utils
import com.squareup.picasso.Picasso
import timber.log.Timber
import javax.inject.Inject

class TracksFragment : BaseFragment<TracksViewModel>(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        fun newInstance() = TracksFragment()
        var TAG = TracksFragment::class.toString()
    }

    private var hasBeenVisibleOnce = false
    @Inject
    lateinit var apiInterface: ApiInterface

    private var page = 1
    private var isNextPage = false
    private var isLoadMore = false
    private var isRefresh = false

    lateinit var mAdapter: CommonAdapter<Track?>

    @Inject
    lateinit var models : TracksViewModel

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        binding.lifecycleOwner = this

        binding.progressBar.backgroundProgressBarColor = ContextCompat.getColor(
            requireActivity(),
            R.color.transparent
        )
        binding.progressBar.backgroundProgressBarWidth = 4F
        binding.progressBar.progressBarWidth = 4F
        binding.progressBar.progressBarColor = ContextCompat.getColor(
            requireActivity(),
            R.color.color_primary
        )
        binding.progressBar.indeterminateMode = true
        binding.progressBar.roundBorder = true

        return binding.root
    }

    override fun getViewModel(): TracksViewModel {
        return models
    }

    override fun setUserVisibleHint(visible: Boolean) {
        super.setUserVisibleHint(true)
        if (this.isVisible) {
            if (visible && !hasBeenVisibleOnce) {

                if(Utils.isNetworkAvailable(requireActivity()))
                {
                    apiInterface.let { models.trackApiCall(
                        USER_GET_TOP_TRACKS,
                        Preferences.getPreferenceString(PrefEntity.USER_NAME)!!, "json",page, apiInterface
                    ) }
                } else {
                    Alerts.showSnackBar(requireActivity(), getString(R.string.internet_not_available))
                }

                hasBeenVisibleOnce = true
            }
        }
    }
  override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        models.trackApiResult().observe(
            viewLifecycleOwner, androidx.lifecycle.Observer
            { result ->

                when (result.status) {
                    AuthStatus.LOADING -> {

                        if(!isRefresh && !isLoadMore)
                            binding.progressBar.visibility = View.VISIBLE
                        binding.tvNoResultTxt.visibility = View.GONE
                    }
                    AuthStatus.ERROR -> {

                        isRefresh = false
                        binding.progressBar.visibility = View.GONE
                        Alerts.showSnackBar(requireActivity(), result.message)

                    }
                    AuthStatus.SUCCESS -> {

                        isRefresh = false
                        binding.progressBar.visibility = View.GONE

                        if (result.data?.toptracks!!.track!!.isNotEmpty()) {

                            //setAdapter(result.data.toptracks.track!!)
                            //binding.tvNoResultTxt.visibility = View.GONE

                                if (result.data.toptracks.attr!!.page!!.toInt() != result.data.toptracks.attr.totalPages!!.toInt()) {
                                    isNextPage = true
                                    page += 1
                                } else {
                                    isNextPage = false
                                }

                                Timber.e("Timber : Page $page")

                                val arrData: ArrayList<Track?> = result.data.toptracks.track!!

                                if (isLoadMore) {
                                    mAdapter.stopLoadMore(arrData)
                                } else {
                                    if (isRefresh) {
                                        if (arrData.size > 0) {
                                            binding.tvNoResultTxt.visibility = View.INVISIBLE
                                            binding.myRecyclerView.visibility = View.VISIBLE
                                            setAdapter(arrData)
                                        }
                                        onItemsLoadComplete()
                                    } else {
                                        if (arrData.size > 0) {
                                            binding.myRecyclerView.visibility = View.VISIBLE
                                            setAdapter(arrData)
                                        } else {
                                            if (isRefresh) {
                                                onItemsLoadComplete()
                                            }
                                            binding.myRecyclerView.visibility = View.GONE
                                            binding.tvNoResultTxt.visibility = View.VISIBLE
                                        }
                                    }
                                }
                                mAdapter.setMoreDataAvailable(isNextPage)

                            } else {
                            binding.tvNoResultTxt.visibility = View.VISIBLE
                            binding.myRecyclerView.visibility = View.GONE
                        }
                    }
                }
            })



        binding.swipeRefreshLayout.setOnRefreshListener(this)
    }

      override fun onRefresh() {
        try {
            page = 1
            isLoadMore = false
            isRefresh = true
            try {
                mAdapter.getData().clear()
                mAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
            }
            if(Utils.isNetworkAvailable(requireActivity())) {
                apiInterface.let { models.trackApiCall(
                    USER_GET_TOP_TRACKS,
                    Preferences.getPreferenceString(PrefEntity.USER_NAME)!!, "json",page, apiInterface
                ) }

                Handler().postDelayed({
                    if (binding.swipeRefreshLayout.isRefreshing) {
                        binding.swipeRefreshLayout.isRefreshing = false
                    }
                }, 800)
            }
            else
            {
                Alerts.showSnackBar(requireActivity(), getString(R.string.internet_not_available))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun onItemsLoadComplete() {
        isRefresh = false
        binding.swipeRefreshLayout.isRefreshing = false
    }


    private fun setAdapter(arrData: ArrayList<Track?>) {
        mAdapter = object : CommonAdapter<Track?>(arrData) {


            override fun getItemViewType(position: Int): Int {
                return if (getItem(position) == null) R.layout.item_load_more else R.layout.row_item_all
            }

            override fun onBind(holder: CommonViewHolder?, position: Int) {
                try {
                    if (holder?.binding is RowItemAllBinding)
                    {
                        val binding: RowItemAllBinding = holder.binding as RowItemAllBinding

                        binding.tvCategoryName.text = arrData[position]?.name

                        Picasso.get()
                            .load(arrData[position]?.image!![2].text)
                            .placeholder(R.drawable.icon_loading_place_holder)
                            .error(R.drawable.icon_loading_place_holder)
                            .into(binding.ivCategoryImage)

                        binding.root.setOnClickListener {

                            val intent = Intent(requireActivity(), DetailViewActivity::class.java)
                            intent.putExtra(DetailViewActivity.SELCTION_TITLE, arrData[position]?.name)
                            intent.putExtra(DetailViewActivity.URL, arrData[position]?.url)
                            startActivity(intent)

                        }
                    } else {
                        val binding: ItemLoadMoreBinding = holder?.binding as ItemLoadMoreBinding
                        binding.progressBar.visibility = View.VISIBLE
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        binding.myRecyclerView.layoutManager = GridLayoutManager(
            requireActivity(),
            2,
            RecyclerView.VERTICAL,
            false
        )
        binding.myRecyclerView.adapter = mAdapter
        binding.myRecyclerView.visibility = View.VISIBLE

        mAdapter.setLoadMoreListener(object : CommonAdapter.OnLoadMoreListener{
            override fun onLoadMore() {
                binding.myRecyclerView.post {
                    mAdapter.startLoadMore()
                    Handler().postDelayed({
                        isLoadMore = true

                        apiInterface.let { models.trackApiCall(
                            USER_GET_TOP_TRACKS,
                            Preferences.getPreferenceString(PrefEntity.USER_NAME)!!, "json",page, apiInterface
                        ) }

                    }, 500)
                }
            }

        })
    }

}