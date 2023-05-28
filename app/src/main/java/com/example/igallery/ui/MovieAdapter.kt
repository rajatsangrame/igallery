//package com.example.igallery.ui
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.imdb.extension.loadImage
//import com.example.imdb.data.model.Movie
//import com.example.imdb.databinding.MovieItemBinding

//
//class MovieAdapter(
//    private var movieList: List<Movie> = ArrayList(),
//    private val listener: (Movie) -> Unit
//) :
//    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
//
//    fun setList(movieList: List<Movie>) {
//        this.movieList = movieList
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding = MovieItemBinding.inflate(
//            layoutInflater,
//            parent, false
//        )
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: MovieAdapter.ViewHolder, position: Int) {
//        holder.bind(movieList[position])
//    }
//
//    override fun getItemCount(): Int {
//        return movieList.size
//    }
//
//    inner class ViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        init {
//            binding.ivPlaylist.setOnClickListener {
//                listener(movieList[adapterPosition])
//            }
//        }
//
//        fun bind(movie: Movie) {
//            binding.ivImage.loadImage("https://image.tmdb.org/t/p/w500${movie.posterPath}")
//            binding.tvTitle.text = movie.title
//            binding.tvRating.text = "Rating: ${movie.voteAverage}"
//        }
//    }
//
//}
