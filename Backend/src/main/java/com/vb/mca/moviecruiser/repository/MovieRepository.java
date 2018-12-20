package com.vb.mca.moviecruiser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vb.mca.moviecruiser.domain.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	
	Movie findByTitle(String title);
}
