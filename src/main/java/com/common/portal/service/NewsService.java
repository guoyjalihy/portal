package com.common.portal.service;

import com.common.portal.controller.vo.NewsVO;
import com.common.portal.dao.NewsRepository;
import com.common.portal.entity.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {
	@Autowired
	NewsRepository newsRepository;

	public NewsVO findById(News news){
		NewsVO result = new NewsVO();
		Optional<News> newsDB = newsRepository.findById(news.getId());
		if (newsDB.get() != null){
			result = buildVO(newsDB.get());
		}
		return  result;
	}

	public List<NewsVO> list(String title, String category){
		List<News> news = newsRepository.findByTitleOrCategory(title,category);
		List<NewsVO> result = new ArrayList<>();
		news.forEach(obj -> {
			NewsVO newsVO = buildVO(obj);
			result.add(newsVO);
		});
		return result;
	}

	private NewsVO buildVO(News obj) {
		NewsVO newsVO = new NewsVO();
		newsVO.setAddDate(obj.getAddDate());
		newsVO.setCategory(obj.getCategory());
		newsVO.setDescription(obj.getCategory());
		newsVO.setTitle(obj.getTitle());
		newsVO.setId(obj.getId());
		return newsVO;
	}

	private News buildEntity(NewsVO newsVO) {
		News news = new News();
		news.setAddDate(newsVO.getAddDate());
		news.setCategory(newsVO.getCategory());
		news.setDescription(newsVO.getCategory());
		news.setTitle(newsVO.getTitle());
		news.setId(newsVO.getId());
		return news;
	}


	public int count(NewsVO news){
		return Integer.valueOf(String.valueOf(newsRepository.countByTitleOrCategory(news.getTitle(),news.getCategory())));
	}

	public void saveOrUpdate(NewsVO newsVO) {
		News news = buildEntity(newsVO);
		newsRepository.saveAndFlush(news);
	}
}
